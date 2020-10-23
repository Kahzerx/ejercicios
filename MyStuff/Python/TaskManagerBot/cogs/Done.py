from discord.ext import commands
from lib.QueryDB import getId, updateCompleted


class Done(commands.Cog, command_attrs=dict(help='Mark tasks as completed')):
    def __init__(self, bot):
        self.bot = bot

    @staticmethod
    def doneHelp():
        return '`.done num(int)`'

    @commands.command()
    async def done(self, ctx):
        msg = ctx.message.content.split(' ')
        if len(msg) == 2:
            try:
                rowId = getId(int(msg[1]), ctx.message.author.id, 0)
                if rowId != -1:
                    updateCompleted(rowId, 1)
                    await ctx.send('`Updated!`')
                else:
                    await ctx.send('`Unable to find the task`')
            except:
                await ctx.send(self.doneHelp())
        else:
            await ctx.send(self.doneHelp())

    @commands.command()
    async def unDone(self, ctx):
        msg = ctx.message.content.split(' ')
        if len(msg) == 2:
            try:
                rowId = getId(int(msg[1]), ctx.message.author.id, 1)
                if rowId != -1:
                    updateCompleted(rowId, 0)
                    await ctx.send('`Updated!`')
                else:
                    await ctx.send('`Unable to find the task`')
            except:
                await ctx.send(self.doneHelp())
        else:
            await ctx.send(self.doneHelp())


def setup(bot):
    bot.add_cog(Done(bot))
