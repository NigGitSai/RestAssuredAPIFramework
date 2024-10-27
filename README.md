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


