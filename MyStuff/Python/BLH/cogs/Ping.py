from discord.ext import commands


class Ping(commands.Cog, command_attrs=dict(help='Check if is alive')):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def ping(self, ctx):
        await ctx.send(f'Pong {round(self.bot.latency * 1000)}ms !')


def setup(bot):
    bot.add_cog(Ping(bot))
