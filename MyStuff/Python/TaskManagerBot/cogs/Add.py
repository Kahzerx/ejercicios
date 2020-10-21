from discord.ext import commands
import datetime


class Add(commands.Cog, command_attrs=dict(help='Add tasks')):
    def __init__(self, bot):
        self.bot = bot

    # @staticmethod
    # def getDate(userInput):
    # return re.search(r'^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\d{4}$', userInput)[0]

    @staticmethod
    def getDate(userInput):
        array = userInput.split(r'/')
        return datetime.datetime(year=int(array[2]), month=int(array[1]), day=int(array[0])).strftime('%d-%m-%Y')

    @staticmethod
    def addHelp():
        return '`.add msg(str) date(dd/mm/yyyy)`'

    @commands.command()
    async def add(self, ctx):
        msg = ctx.message.content.split(' ')
        if len(msg) == 3:
            try:
                date = self.getDate(msg[2])
                print(date)
                note = msg[1]
                print(note)
            except:
                await ctx.send(self.addHelp())
        else:
            await ctx.send(self.addHelp())


def setup(bot):
    bot.add_cog(Add(bot))
