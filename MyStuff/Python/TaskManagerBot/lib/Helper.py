from discord import Embed, Color


def getEmbedWithField(data, cFlag, name, inline):
    embed = Embed(
        title='Estado del virus:\n',
        color=Color.red()
    )
    for msg, val in data.items():
        embed.add_field(name=msg, value=val, inline=inline)

    if cFlag != '':
        embed.set_author(name=name, icon_url=cFlag)

    return embed


def checkCountry(country):
    if country == 'korea':
        country = 'S. Korea'
    return country
