from discord.ext import commands
import datetime
from lib.QueryDB import insertTask


class Add(commands.Cog, command_attrs=dict(help='Add tasks')):
    def __init__(self, bot):
        self.bot = bot

    # @staticmethod
    # def getDate(userInput):
    # return re.search(r'^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\d{4}$', userInput)[0]

    @staticmethod
    def getDate(userInput):
        array = userInput.split(r'/')
        return datetime.datetime(year=int(array[2]), month=int(array[1]), day=int(array[0])).strftime('%d-%m-%y')

    @staticmethod
    def addHelp():
        return '`.add msg(str) date(dd/mm/yy)`'

    @commands.command()
    async def add(self, ctx):
        msg = ctx.message.content.split(' ')
        try:
            userId = ctx.message.author.id
            message = '_'.join(msg[1:-1])
            date = self.getDate(msg[-1])

            insertTask(userId, message, date, 0)

            await ctx.send('Task added!')

        except:
            await ctx.send(self.addHelp())


def setup(bot):
    bot.add_cog(Add(bot))
