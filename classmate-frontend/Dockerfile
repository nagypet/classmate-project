FROM nginx:latest

#COPY dist/app/* /usr/share/app/html/
COPY dist/ /usr/share/app/html/
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/nginx.conf
