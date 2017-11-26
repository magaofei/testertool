package jmx;

//import java.io.File;
//import java.io.FileOutputStream;


//import hello.Modal.JmxHttpSampler;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;


import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 产生JMX文件
 * 需要传入数据
 * 需要设置 JMeter 路径
 */

public class JMXCreator {
    public static String createJmxFile (
            HTTPSampler httpSampler, LoopController loopController, ThreadGroup threadGroup)
            throws IOException {

//        File file = new File("production.properties");
//        FileInputStream fileInput = new FileInputStream(file);
//        Properties properties = new Properties();
//        properties.load(fileInput);
//        fileInput.close();

        // Initialize the configuration variables
//        String savePath = properties.getProperty("savePath");
//        String jmeterHome = properties.getProperty("jmeterHome");
//        String savePath = "/Users/apple/Downloads/";
//        String jmeterHome = "/Users/apple/Documents/apache-jmeter-3.3";
        String savePath = "/data/Downloads/";
        String jmeterHome = "/data/apache-jmeter-3.3";

        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir()
                + "/jmeter.properties");
//        JMeterUtils.initLogging();
        JMeterUtils.initLocale();

        HashTree testPlanTree = new HashTree();

        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSampler.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        // 根据不同的接口划分不同
//        HTTPArgument httpArgument = new HTTPArgument();
//        httpArgument.setName("Foo");
//        httpArgument.setValue("");
//        httpArgument.setAlwaysEncoded(false);
//        httpArgument.setUseEquals(true);
//        httpArgument.setMetaData("=<");


        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());

        loopController.initialize();

        // Thread Group
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
        threadGroupHashTree.add(httpSampler);

        // save generated test plan to JMeter's .jmx file format
//        SaveService.saveTree(testPlanTree, new FileOutputStream("report\\jmeter_api_sample.jmx"));

        // 添加时间戳
        long currentTime = System.currentTimeMillis();
        String name = httpSampler.getName();
        String domain = httpSampler.getDomain();
        String saveFileName = name + "-" + domain + "-" + currentTime + ".jmx";

        String fullFilePath = savePath + saveFileName;
        // Save to jmx file
        SaveService.saveTree(testPlanTree, new FileOutputStream(
                fullFilePath));
        return fullFilePath;

        // 判断如果文件存在就返回成功
//        File file = new File(fullFilePath);
//        return file.exists();

    }
}