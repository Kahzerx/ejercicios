# https://jarroba.com/anonymous-scraping-by-tor-network/

import requests
from time import sleep
from stem import Signal
from stem.control import Controller
import threading


class ThreadTest(threading.Thread):
    def __init__(self, num, time):
        super().__init__()
        self.num = num
        self.time = time

    def run(self) -> None:
        renew_connection(self.time)
        print(f'Connection no {self.num}')
        get_tor_connection()


def get_normal_connection():
    with requests.Session() as s:
        result = s.get("https://ipinfo.io/").text
        print(result)


def get_tor_connection():
    with requests.Session() as s:
        proxies = {'http': 'socks5://127.0.0.1:9050', 'https': 'socks5://127.0.0.1:9050'}
        result = s.get("https://ipinfo.io/", proxies=proxies).text
        print(result)
        # socks5h protocol in order to enable remote DNS resolving in case the local DNS resolving process fails.
        # See https://github.com/kennethreitz/requests/blob/e3f89bf23c53b98593e4248054661472aacac820/requests/packages/urllib3/contrib/socks.py#L158
        proxies2 = {'http': 'socks5h://127.0.0.1:9050', 'https': 'socks5h://127.0.0.1:9050'}
        result2 = s.get("https://3g2upl4pq6kufc4m.onion/", proxies=proxies2).text
        print(result2)


def renew_connection(time):
    with Controller.from_port(port=9051) as controller:
        controller.authenticate(password="test123")
        controller.signal(Signal.NEWNYM)
    sleep(time)


if __name__ == "__main__":
    for i in range(50):
        t = ThreadTest(i, 8)
        t.start()
        sleep(8)
