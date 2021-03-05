FROM centos:7

WORKDIR /var/www/html

RUN yum -y install \
    httpd \
    php php-cli php-common \
    mod_ssl openssl

COPY myweb .

RUN echo "<?php phpinfo(); ?>" > info.php

COPY ssl.conf /etc/httpd/conf.d/default.conf

COPY dockerTests.crt /dockerTests.crt

COPY dockerTests.key /dockerTests.key

EXPOSE 443

CMD apachectl -DFOREGROUND