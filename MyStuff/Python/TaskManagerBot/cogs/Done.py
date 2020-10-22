from discord.ext import commands


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
                num = int(msg[1])
            except:
                await ctx.send(self.doneHelp())
        else:
            await ctx.send(self.doneHelp())


def setup(bot):
    bot.add_cog(Done(bot))
