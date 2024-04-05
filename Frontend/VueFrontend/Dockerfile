FROM node:lts AS build-stage
WORKDIR /app

# Copy package manager files
COPY package.json ./
COPY package-lock.json ./

# Install project dependencies
RUN npm install

# Copy the source code, public folder, and any other necessary files
COPY src/ src/
COPY public/ public/
COPY .eslintrc.cjs ./
COPY .prettierrc.json ./
COPY vite.config.js ./
COPY vitest.config.js ./
COPY cypress.config.js ./
COPY index.html ./

# Build your app
RUN npm run build

# Production stage
FROM nginx:stable AS production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Copy custom Nginx configuration
COPY default.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
