# etapa de compilación
FROM node:current-alpine as build-stage
WORKDIR app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

# etapa de producción
FROM nginx:1.19.2-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]