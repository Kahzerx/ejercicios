import requests
from time import sleep
from stem import Signal
from stem.control import Controller


def get_normal_connection():
    with requests.Session() as s:
        result = s.get("https://ipinfo.io/").text
        print(result)


def get_tor_connection():
    while True:
        with requests.Session() as s:
            proxies = {'http':  'socks5://127.0.0.1:9050', 'https': 'socks5://127.0.0.1:9050'}
            result = s.get("https://ipinfo.io/", proxies=proxies).text
            print(result)
        renew_connection()
        sleep(7)


def renew_connection():
    with Controller.from_port(port=9051) as controller:
        controller.authenticate(password="test123")
        controller.signal(Signal.NEWNYM)


if __name__ == "__main__":
    # get_normal_connection()
    get_tor_connection()
