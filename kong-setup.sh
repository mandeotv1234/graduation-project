#!/bin/bash
# filepath: e:\my-graduation-project\kong-setup.sh

# Wait for Kong to be ready
echo "Waiting for Kong to be ready..."
sleep 30

# Kong Admin API URL
KONG_ADMIN_URL="http://localhost:8001"

# Create User Service
echo "Creating User Service..."
curl -i -X POST $KONG_ADMIN_URL/services/ \
  --data "name=user-service" \
  --data "url=http://user-service:8081"

# Create route for User Service
echo "Creating route for User Service..."
curl -i -X POST $KONG_ADMIN_URL/services/user-service/routes \
  --data "paths[]=/api/users" \
  --data "strip_path=true"

# Create Discovery Service (for monitoring)
echo "Creating Discovery Service..."
curl -i -X POST $KONG_ADMIN_URL/services/ \
  --data "name=discovery-service" \
  --data "url=http://discovery-server:8761"

# Create route for Discovery Service
echo "Creating route for Discovery Service..."
curl -i -X POST $KONG_ADMIN_URL/services/discovery-service/routes \
  --data "paths[]=/eureka" \
  --data "strip_path=false"

# Add Rate Limiting Plugin
echo "Adding rate limiting plugin..."
curl -i -X POST $KONG_ADMIN_URL/services/user-service/plugins \
  --data "name=rate-limiting" \
  --data "config.minute=100" \
  --data "config.hour=1000"

# Add CORS Plugin
echo "Adding CORS plugin..."
curl -i -X POST $KONG_ADMIN_URL/services/user-service/plugins \
  --data "name=cors" \
  --data "config.origins=*" \
  --data "config.methods=GET,POST,PUT,DELETE,OPTIONS" \
  --data "config.headers=Accept,Accept-Version,Content-Length,Content-MD5,Content-Type,Date,X-Auth-Token,Authorization"

# Add Request/Response Logging
echo "Adding logging plugin..."
curl -i -X POST $KONG_ADMIN_URL/services/user-service/plugins \
  --data "name=file-log" \
  --data "config.path=/tmp/kong-access.log"

echo "Kong setup completed!"