package Resources;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ExtentReportListener {

	protected static  ExtentSparkReporter report;
	protected static ExtentReports extent;
	static ExtentTest test;
	static String dateTime = "";
	static String reportName= "";
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();

	public static ExtentTest getReport()
	{
		return testReport.get();
	}
	protected static void startReport()
	{
		reportName="APIAutomationReport_"+fetchCurrentDateTimeForReports()+".html";
		System.out.println(reportName);

		report = new ExtentSparkReporter("./Reports/"+reportName);
		report.config().setEncoding("utf-8");
		report.config().setDocumentTitle("Place API Testing");
		report.config().setTheme(Theme.STANDARD);
		report.config().setTimeStampFormat("dd-MM-yyyy hh:mm:ss");
		report.config().setReportName("API Automation Report");

		extent = new ExtentReports();
		extent.attachReporter(report);
		extent.setSystemInfo("System", "Windows 11");
		extent.setSystemInfo("Author", "Nigalyasree");
		extent.setSystemInfo("Build#", "1.1");
		extent.setSystemInfo("Team", "Automation QA");
		extent.setSystemInfo("Customer Name", "NAL");
	}

	protected void createTest(String testCaseName)
	{
		System.out.println(extent+" in Create test");
		test = extent.createTest(testCaseName);
		testReport.set(test);
	}

	protected void testStatus(String status, String message)
	{
		switch (status.toLowerCase()) {
		case "pass":
			getReport().pass(message);
			break;
		case "fail":
			getReport().fail(message);
			break;
		case "info":
			getReport().info(message);
			break;
		case "warning":
			getReport().warning(message);
			break;
		default:
		{
			getReport().log(Status.INFO, message);
			break;
		}
		}
	}

	protected static String fetchCurrentDateTimeForReports()
	{
		Date dt = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_hhmmss");
		//System.out.println(System.currentTimeMillis());
		dateTime = format.format(dt);
		return dateTime;

	}



}
