# API Key Setup

This project requires a CoinRanking API key to function properly.

## Setup Instructions

1. Copy the template file:
   ```
   cp composeApp/src/commonMain/kotlin/com/example/cryptowallet/app/core/network/HttpClientFactory.kt.template composeApp/src/commonMain/kotlin/com/example/cryptowallet/app/core/network/HttpClientFactory.kt
   ```

2. Get your API key from [CoinRanking](https://coinranking.com/)

3. Open the newly created `HttpClientFactory.kt` file and replace `YOUR_API_KEY_HERE` with your actual API key

4. The `HttpClientFactory.kt` file is in `.gitignore` and will not be committed to version control

**Note:** Never commit your actual API key to version control!

