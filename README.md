# properties-fetcher
App that fetches properties from Yandex Cloud Lockbox to the env variables

Usage example:
```bash
#!/bin/bash

PASTE_SECRET_ID=<secret_id>
PROPERTIES_FILE=lockbox.properties

java -jar properties-fetcher-1.1.jar $YC_OAUTH_TOKEN $PASTE_SECRET_ID $PROPERTIES_FILE
java -jar app.jar --spring.config.import=$PROPERTIES_FILE
```
