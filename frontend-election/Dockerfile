# Use a lightweight Node.js image (with Alpine for smaller size)
FROM node:alpine

# Set the working directory where the application will be stored
WORKDIR /opt/app

# Create the dist directory in the container (if not already created)
RUN mkdir -p /opt/app/dist

# Copy the pre-built front-end files from your local `dist` folder into the container
COPY ./dist ./dist

# Install http-server globally to serve the static files
RUN npm install -g http-server

# Set the port (you can set a default here or use an environment variable)
ARG PORT=3000
ENV PORT=${PORT}

# Expose the port that your app will run on
EXPOSE ${PORT}

# Serve the front-end using http-server, with the specified port
ENTRYPOINT ["npx", "http-server", "-p", "3000", "./dist"]

