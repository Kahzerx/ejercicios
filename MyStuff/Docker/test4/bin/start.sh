#!/bin/bash

# php process in the background
/usr/sbin/php-fpm -c /etc/php/fpm

# starts nginx daemon
nginx -g 'daemon off;'