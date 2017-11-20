package jmx;

//import java.io.File;
//import java.io.FileOutputStream;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.java.control.gui.JavaTestSamplerGui;
import org.apache.jmeter.protocol.java.sampler.JavaSampler;

import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 产生JMX文件
 * 需要传入数据
 * 需要设置 JMeter 路径
 */

public class JMXCreator {
    public static boolean createJmxFile (
            String name, String domain, int port, String method, String path, int loops)
            throws IOException {
        
        // Initialize the configuration variables
        String savePath = "/Users/mark/Downloads/";
        String jmeterHome = "/Users/mark/Documents/apache-jmeter-3.3";

//        String name = "baidu";
//        String domain = "www.baidu.com";

        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir()
                + "/jmeter.properties");
//        JMeterUtils.initLogging();
        JMeterUtils.initLocale();

        HashTree testPlanTree = new HashTree();

        // First HTTP Sampler - open uttesh.com
        HTTPSamplerProxy examplecomSampler = new HTTPSamplerProxy();

        examplecomSampler.setDomain(domain);
        examplecomSampler.setPort(port);
        examplecomSampler.setPath(path);
        examplecomSampler.setMethod(method);
        examplecomSampler.setName(name);
        examplecomSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        examplecomSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());


        // Loop Controller
        LoopController loopController = new LoopController();
        loopController.setLoops(loops);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        // Thread Group
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Sample Thread Group");
        threadGroup.setNumThreads(1);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        // Test Plan
        TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");

        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        // Construct Test Plan from previously initialized elements
        testPlanTree.add(testPlan);
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        threadGroupHashTree.add(examplecomSampler);

        // save generated test plan to JMeter's .jmx file format
//        SaveService.saveTree(testPlanTree, new FileOutputStream("report\\jmeter_api_sample.jmx"));

        // 添加时间戳
        long currentTime = System.currentTimeMillis();
        String saveFileName = name + "-" + domain + "-" + currentTime + ".jmx";

        String fullFilePath = savePath + saveFileName;
        // Save to jmx file
        SaveService.saveTree(testPlanTree, new FileOutputStream(
                fullFilePath));

        // 判断如果文件存在就返回成功
        File file = new File(fullFilePath);
        return file.exists();

    }
}