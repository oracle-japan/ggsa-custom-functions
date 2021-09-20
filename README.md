# ggsa-custom-functions

GoldenGate Stream Analyticsで利用するカスタム・ファンクションです。  
主にjsonの操作を行うためのユーティリティを提供しています。  

Javadocはこちらで参照できます。  
<https://oracle-japan.github.io/ggsa-custom-functions/apidocs/>

## jarファイルの作り方

パッケージ済みのカスタム・ファンクション jar ファイルを直接提供できませんので、ソースから jarファイルを作成してください。  
ソースからコンパイル/パッケージするには、GoldenGate Stream Analytics のインストール・ディレクトリ配下に存在する `osa.spark-cql.extensibility.api.jar` が必要で、この中にカスタム・ファンクションが利用するアノテーションが含まれます。

作成には JDK と Maven が必要です。

1. ソースをダウンロードする
    
    GoldenGate Stream Analytics がインストールされているサーバにダウンロードします。
    
    ```
    $ git clone https://github.com/oracle-japan/ggsa-custom-functions.git
    $ cd ggsa-custom-functions
    ```

2. OSA_HOME 環境変数を設定する

    GoldenGate Stream Analytics のインストール・ディレクトリを指定します。  
    (例)

    ```bash
    $ export OSA_HOME=/opt/OSA-19.1.0.0.6.1
    ```

3. Mavenを使ってJarファイルを作成する  

    ```bash
    $ mvn package
    ```

    target/ggsa-custom-functions-x.x.x.jar が作成されます。



