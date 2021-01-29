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
