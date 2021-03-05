FROM nginx

RUN useradd kahzerx

WORKDIR /usr/share/nginx/html

COPY myweb .

ENV archivo docker

RUN echo "$archivo" > env.html

EXPOSE 90

LABEL version=1.0

USER kahzerx

RUN echo "I am $(whoami)." > /tmp/me.html

USER root

RUN mv /tmp/me.html docker.html

VOLUME /var/log/nginx

# En este caso podemos dejar CMD en blanco, ya que por defecto la imagen de nginx ha establecido una.
CMD nginx -g 'daemon off;'