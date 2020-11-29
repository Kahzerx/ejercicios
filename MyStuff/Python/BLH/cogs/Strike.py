from discord.ext import commands
from lib.QueryDB import userExists, addStrike, updateStrikes, getStrikes
import re
from datetime import date


class Strike(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @staticmethod
    def getDate():
        return date.today().strftime("%d/%m/%Y")

    @staticmethod
    def getId(userId):
        return re.search(r'\d+', userId)[0]

    @staticmethod
    def strikeHelp():
        return '`.add <@!389514280892104715> <motivo>`'

    @commands.command()
    async def strike(self, ctx):
        msg = ctx.message.content.split(' ')
        # noinspection PyBroadException
        try:
            userId = int(self.getId(msg[1]))
            userName = str(await self.bot.fetch_user(userId))
            if userName is None:
                return
            reason = ' '.join(msg[2:])
            date1 = self.getDate()
            if userExists(userId):
                updateStrikes(userId, getStrikes(userId))
            else:
                addStrike(userId, userName, reason, 1, date1)
        except Exception:
            await ctx.send(self.strikeHelp())


def setup(bot):
    bot.add_cog(Strike(bot))
