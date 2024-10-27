This API automation framework is designed using Java, Cucumber, and Rest Assured, with support for reporting through Extent Reports and maven-cucumber-reporting. It follows a layered architecture for better readability, modularization, and ease of maintenance.

src
├── main
│   └── java
│       ├── pojo                 # POJO classes for data modeling
│       └── Resources            # Utilities and reporting setup for the framework
│           ├── APIResources     # Enum for defining API path resources
│           ├── ExtentReportListener # Extent Spark Reporter configuration
│           ├── TestDataBuild    # Builds request payloads
│           └── Utils            # Utility functions, including request specifications and JSON path parsing
└── test
    ├── java
    │   ├── cucumber.options     # Contains TestRunner for Cucumber tests
    │   └── stepDefinitions      # Step Definitions for Cucumber feature files
    └── resources
        ├── features             # .feature files for test scenarios
        └── config               # Global configuration properties (e.g., base URI)



src/main/java
pojo: Contains POJO (Plain Old Java Object) classes for easy data modeling and deserialization of JSON responses.
Resources
APIResources: Enum class to define API endpoints for a consistent resource path reference.
ExtentReportListener: Sets up Extent Spark Reporter for comprehensive HTML reporting after test execution.
TestDataBuild: Constructs request payloads for various API requests, supporting reusable test data across scenarios.
Utils: Includes utilities such as request specification builders, JSON deserialization functions (Rest Assured and Jayway JSON Path).
src/test/java
cucumber.options: Contains the TestRunner class, which serves as the entry point for executing Cucumber tests.
stepDefinitions: Step Definitions package for mapping Cucumber feature file steps to Java methods, executing actions, and validations.
src/test/resources
Features: Directory for .feature files that define test scenarios in Gherkin syntax, specifying behavior for different APIs.
Config: Contains configuration files, such as config.properties, to manage environment settings, base URIs, and other properties.
