package jmx;

import java.io.FileOutputStream;

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




public class JMXCreator {
    public static void main(String[] argv) throws Exception {
        // Initialize the configuration variables
        String savePath = "/Users/mark/Downloads/test.jmx";
        String jmeterHome = "/Users/mark/Documents/apache-jmeter-3.3";
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir()
                + "/jmeter.properties");
//        JMeterUtils.initLogging();
        JMeterUtils.initLocale();

//        // TestPlan
//        TestPlan testPlan = new TestPlan();
//        testPlan.setName("Test Plan");
//        testPlan.setEnabled(true);
//        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
//        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
//
//        // ThreadGroup controller
//        LoopController loopController = new LoopController();
//        loopController.setEnabled(true);
//        loopController.setLoops(5);
//        loopController.setProperty(TestElement.TEST_CLASS,
//                LoopController.class.getName());
//        loopController.setProperty(TestElement.GUI_CLASS,
//                LoopControlPanel.class.getName());
//
//        // ThreadGroup
//        ThreadGroup threadGroup = new ThreadGroup();
//        threadGroup.setName("Thread Group");
//        threadGroup.setEnabled(true);
//        threadGroup.setSamplerController(loopController);
//        threadGroup.setNumThreads(5);
//        threadGroup.setRampUp(10);
//        threadGroup.setProperty(TestElement.TEST_CLASS,
//                ThreadGroup.class.getName());
//        threadGroup.setProperty(TestElement.GUI_CLASS,
//                ThreadGroupGui.class.getName());
//
//        // JavaSampler
////        JavaSampler javaSampler = new JavaSampler();
////        javaSampler.setClassname("my.example.sampler");
////        javaSampler.setEnabled(true);
////        javaSampler.setProperty(TestElement.TEST_CLASS,
////                JavaSampler.class.getName());
////        javaSampler.setProperty(TestElement.GUI_CLASS,
////                JavaTestSamplerGui.class.getName());
//        HTTPSamplerProxy httpSamplerProxy = new HTTPSamplerProxy();
//
////        HTTPSampler httpSampler = new HTTPSampler();
//        httpSamplerProxy.setEnabled(true);
//        httpSamplerProxy.setProperty(TestElement.TEST_CLASS,
//                HTTPSamplerProxy.class.getName());
//        httpSamplerProxy.setProperty(TestElement.GUI_CLASS,
//                HttpTestSampleGui.class.getName());
//
//        httpSamplerProxy.setPort(80);
//        httpSamplerProxy.setDomain("www.baidu.com");
//        httpSamplerProxy.setPath("/");
//        httpSamplerProxy.setMethod("GET");
//
//
//        // Create TestPlan hash tree
//        HashTree testPlanHashTree = new HashTree();
//        testPlanHashTree.add(testPlan);
//
//        // Add ThreadGroup to TestPlan hash tree
//        HashTree threadGroupHashTree = new HashTree();
//        threadGroupHashTree = testPlanHashTree.add(testPlan, threadGroup);
//
//        // Add Java Sampler to ThreadGroup hash tree
////        HashTree javaSamplerHashTree = new HashTree();
//        threadGroupHashTree.add(httpSamplerProxy);

        HashTree testPlanTree = new HashTree();

        // First HTTP Sampler - open uttesh.com
        HTTPSamplerProxy examplecomSampler = new HTTPSamplerProxy();
        examplecomSampler.setDomain("uttesh.com");
        examplecomSampler.setPort(80);
        examplecomSampler.setPath("/");
        examplecomSampler.setMethod("GET");
        examplecomSampler.setName("Open uttesh.com");
        examplecomSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        examplecomSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());


        // Loop Controller
        LoopController loopController = new LoopController();
        loopController.setLoops(1);
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



        // Save to jmx file
        SaveService.saveTree(testPlanTree, new FileOutputStream(
                savePath));
    }
}