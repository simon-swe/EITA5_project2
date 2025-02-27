openssl genpkey -algorithm RSA -out ca.key -pkeyopt rsa_keygen_bits:4096

openssl req -x509 -new -key ca.key -sha256 -days 365 -out ca.crt -subj "/CN=CA"

openssl x509 -outform der -in ca.crt -out ca.der

keytool -importcert -keystore clienttruststore -storepass password -noprompt -alias  ca -file ca.der

keytool -genkeypair -alias client -keyalg RSA -keysize 2048 -validity 365 -keystore clientkeystore -storepass password -dname "CN=THOMAS TILLGREN (th7403ti-s)/ JULIUS LAM (ju2017la-s)/ SIMON SWEDENBORG (si5431sw-s)/ HANNA VALLIN (ha0531va-s)"

keytool -certreq -alias client -file client.csr -keystore clientkeystore -storepass password

openssl x509 -req -in client.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out client.crt -days 365 -sha256

keytool -importcert -keystore clientkeystore -storepass password -alias ca -file ca.crt

keytool -importcert -keystore clientkeystore -storepass password -alias client -file client.crt

keytool -genkeypair -alias server -keyalg RSA -keysize 2048 -validity 365 -keystore serverkeystore -storepass password -dname "CN=MyServer"

keytool -certreq -alias server -file server.csr -keystore serverkeystore -storepass password

openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt -days 365 -sha256

keytool -importcert -keystore serverkeystore -storepass password -alias ca -file ca.crt

keytool -importcert -keystore serverkeystore -storepass password -alias server -file server.crt

keytool -importcert -keystore servertruststore -storepass password -noprompt -alias ca -file ca.crt
