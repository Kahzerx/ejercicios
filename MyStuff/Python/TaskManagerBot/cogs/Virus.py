from discord.ext import commands
from lib.API import getAllCovidData, getCountryCovidData
from lib.Helper import getEmbedWithField, checkCountry


class Virus(commands.Cog, command_attrs=dict(help='Covid-19 info')):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def virus(self, ctx):
        if len(ctx.message.content.split()) == 1:
            # noinspection PyBroadException
            try:
                await ctx.send(embed=getEmbedWithField(getAllCovidData(), '', '', True))

            except Exception:
                await ctx.send('`No se pudo establecer conexión con el servidor`')

        else:
            # noinspection PyBroadException
            try:
                country = ' '.join(ctx.message.content.split()[1:])
                country = checkCountry(country)
                dictionary, flag, name = getCountryCovidData(country)
                await ctx.send(embed=getEmbedWithField(dictionary, flag, name, True))
            except Exception:
                await ctx.send('`País no encontrado`')


def setup(bot):
    bot.add_cog(Virus(bot))
