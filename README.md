# properties-fetcher
App that fetches properties from Yandex Cloud Lockbox to the env variables

Usage example to fetch properties and pass it to `myCoolApp`:
```bash
#!/bin/bash

YC_OAUTH_TOKEN=<token>
PASTE_SECRET_ID=<secret_id>
PROPERTIES_FILE=lockbox.properties

java -jar properties-fetcher.jar $YC_OAUTH_TOKEN $PASTE_SECRET_ID $PROPERTIES_FILE
java -jar myCoolApp.jar --spring.config.import=$PROPERTIES_FILE
```
