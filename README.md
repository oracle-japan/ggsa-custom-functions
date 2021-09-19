# ggsa-custom-functions

GoldenGate Stream Analyticsで利用するカスタム・ファンクションです。  
主にjsonの操作を行うためのユーティリティを提供しています。  

Javadocはこちらで参照できます。  
<https://oracle-japan.github.io/ggsa-custom-functions/apidocs/>

## jarファイルの作り方

ソースからコンパイル/パッケージするには、GoldenGate Stream Analytics のインストール・ディレクトリ配下に存在する osa.spark-cql.extensibility.api.jar が必要で、この中にカスタム・ファンクションが利用するアノテーションが含まれます。という訳で、パッケージ済みのカスタム・ファンクション jar ファイルを直接提供できませんので、ソースから jarファイルを作成してください。

1. OSA_HOME 環境変数を設定する

    GoldenGate Stream Analytics のインストールディレクトリを指定します。  
    (例)

    ```bash
    $ export OSA_HOME=/opt/OSA-19.1.0.0.6.1
    ```

2. Mavenを使ってJarファイルを作成する  

    ```bash
    $ mvn package
    ```

    target/ggsa-custom-functions-x.x.x.jar が作成されます。



