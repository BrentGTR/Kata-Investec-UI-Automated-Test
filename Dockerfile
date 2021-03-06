FROM openjdk:8-jre-slim

        # Add the suite xmls
        ADD order-module.xml /usr/share/tag/order-module.xml
        ADD search-module.xml /usr/share/tag/search-module.xml

        # Command line to execute the test
        # Expects below ennvironment variables
        # BROWSER = chrome / firefox
        # MODULE  = order-module / search-module
        # SELENIUM_HUB = selenium hub hostname / ipaddress

        ENTRYPOINT /usr/bin/java -cp /usr/share/tag/container-test.jar -DseleniumHubHost=$SELENIUM_HUB -Dbrowser=$BROWSER org.testng.TestNG /usr/share/tag/$MODULE