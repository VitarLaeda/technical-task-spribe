# _Test task_
### by Vitalii Lialchenko

### Includes:
- Java 11
- RestAssured
- Allure Report
- Basic Logging 

### Config 
> ***_Config_*** parameters can be found in [config.properties](src/main/resources/config.properties) \
> Possible configuration parameters:
> > **envURL**: base URL for project \
> > **envType**: name of environment \
> > **logLevel**: logging level for TestNG and AllureLifecycle \
> > **thread-count**: TestNG parameter for parallel runs \
> > **parallel**: TestNG parameter one of \[methods, tests, classes, instances\]
> 
> ***_Allure_*** config can be found in [config.properties](src/main/resources/allure.properties) 
> > it define default directory for allure results `target/allure-results`
> 
> ***_Logger_*** configured in [logback.xml](src/main/resources/logback.xml)
> > test-run logs can be founded in [target](target) directory [test-run.log](target/logs/test-run.log)
