# 在庫管理Webシステム

名前まで決めきれなかった。。。。  

## 環境

- Spring Boot v2.5.5
- Tomcat v9.0.53

## 開発環境

- Spring Tool Suite 4
- Visual Studio Code
- XAMMP (MySQL)

## 実行

Spring Bootなのでデータベースはアクセスさえできれば自動生成されるはずです。  
`src/main/resources`内の`application_default.properties`を  
`application.properties`にリネームして各種設定を行ってから  
実行してください。  

コピペで動かすと、「XAMMPのデフォルト環境」のMySQLに、  
データベース「`product_manager`」を追加することで動作する...はず。

現状ユーザー作成画面まで完成していないので、  
ログインユーザを手動でデータベースに追加する必要があります。  
テーブル`user`に対して、以下のデータを設定してください。

| user_id | user_name | password |
| ---- | ---- | ---- |
| 1 | <任意のユーザ名> | <SHA256でハッシュ化されたパスワード> |

## reCAPTCHA

`application.properties`にサイトキーとシークレットキー入れると  
ログインフォームにいつものヤツが出現するはずです。
