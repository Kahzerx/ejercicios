import requests
import json


def getData(req):
    data = {'Casos Totales': json.loads(req.content.decode('utf-8'))['cases'],
            'Casos Hoy': json.loads(req.content.decode('utf-8'))['todayCases'],
            'Muertes Totales': json.loads(req.content.decode('utf-8'))['deaths'],
            'Muertes Hoy': json.loads(req.content.decode('utf-8'))['todayDeaths'],
            'Recuperados Totales': json.loads(req.content.decode('utf-8'))['recovered'],
            'Recuperados Hoy': json.loads(req.content.decode('utf-8'))['todayRecovered']}

    return data


def getAllCovidData():
    req = requests.get('https://corona.lmao.ninja/v3/covid-19/all')

    return getData(req)


def getCountryCovidData(c):
    req = requests.get(f'https://corona.lmao.ninja/v3/covid-19/countries/{c}')

    flag = json.loads(req.content.decode('utf-8'))['countryInfo']['flag']
    name = json.loads(req.content.decode('utf-8'))['country']

    return getData(req), flag, name
