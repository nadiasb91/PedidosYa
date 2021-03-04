import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.vimalselvam.cucumber.listener.Reporter;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;

import java.io.File;

@CucumberOptions(
        features = {"src/test/resources"},
        glue = {"StepDefinition"},
        plugin = {"html:com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class TestRunner extends AbstractTestNGCucumberTests {
    @AfterClass
    public static void writeExtentReport() {
        File file = new File("/Users/nadiasanchez/IdeaProjects/PedidosYa/src/test/extent-config.xml");
        ExtentHtmlReporter reporter = new ExtentHtmlReporter(System.getProperty("/Users/nadiasanchez/IdeaProjects/PedidosYa/com.cucumber.listener.ExtentCucumberFormatter:target/report") + "report-extent.html");
        reporter.loadXMLConfig(file);


    }
}
