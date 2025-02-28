#!/bin/bash
# create_client_certificates.sh
# This script generates client keystores, CSRs, and signed certificates for a list of users.
# CA files (ca.key, ca.crt, ca.der) must exist in the TLS_CA folder.
# Each user gets a unique directory under TLS_User/<username>.

# Create base directories if they don't exist
# Loop over each user from a here-document.
# Format per line: username,something,role,department
while IFS=, read -r username something role department; do
    # Skip empty lines
    [ -z "$username" ] && continue

    echo "Processing user: $username"

    # Create the user's directory
    user_dir="TLS_Users/$username"
    mkdir -p "$user_dir"

    # Import the CA certificate into the client's truststore.
    # If the truststore doesn't exist, keytool will create it.
    keytool -importcert -noprompt -keystore "$user_dir/clienttruststore" -storepass "passwd" -alias ca -file TLS_CA/ca.der

    # Construct the distinguished name (dname) for the client.
    # If the department field is empty, omit it.
    if [ -z "$department" ]; then
        dname="CN=$username, OU=$role"
    else
        dname="CN=$username, OU=$role, O=$department"
    fi

    # Generate the client key pair in the client keystore.
    keytool -genkeypair -alias client -keyalg RSA -keysize 2048 -validity 365 \
      -keystore "$user_dir/clientkeystore" -storepass "passwd" -dname "$dname"

    # Create a Certificate Signing Request (CSR) for the client.
    keytool -certreq -alias client -file "$user_dir/client.csr" \
      -keystore "$user_dir/clientkeystore" -storepass "passwd"

    # Sign the client certificate using the CA.
    openssl x509 -req -in "$user_dir/client.csr" -CA TLS_CA/ca.crt -CAkey TLS_CA/ca.key \
      -CAcreateserial -out "$user_dir/client.crt" -days 365 -sha256

    # Import the CA certificate into the client keystore.
    keytool -importcert -noprompt -keystore "$user_dir/clientkeystore" -storepass "passwd" \
      -alias ca -file TLS_CA/ca.crt

    # Import the signed client certificate into the client keystore.
    keytool -importcert -noprompt -keystore "$user_dir/clientkeystore" -storepass "passwd" \
      -alias client -file "$user_dir/client.crt"

    echo "Finished processing user: $username"
    echo "---------------------------------------"
done <<EOF
Sara,data123,Doctor,Heart
Henrik,heart1,Doctor,Heart
Daniel,lungs2,Nurse,Lungs
Oliver,heart2,Patient,Heart
Adam,data1,Nurse,Lungs
Alice,dat2,Patient,Lungs
Elsa,fatal2,Government,
Per,fatal3,Government,
EOF
