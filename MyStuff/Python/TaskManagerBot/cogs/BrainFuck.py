from discord.ext import commands
from lib.BrainFuckCompiler import processCode


class BrainFuck(commands.Cog, command_attrs=dict(help='BrainFuck compiler')):
    def __init__(self, bot):
        self.bot = bot
    
    @staticmethod
    def getCode(userInput):
        validChars = '+-<>[].'
        finalString = ''
        for char in userInput:
            if char in validChars:
                finalString += char
        return finalString

    @commands.command()
    async def bf(self, ctx):
        if len(ctx.message.content.split(' ')) >= 2:
            msg = ''.join(ctx.message.content.split(' ')[1:])
            code = self.getCode(msg)
            if code != '':
                await ctx.send(processCode(code))
            
            else:
                await ctx.send('`Input valid BrainFuck`')
        
        else:
            await ctx.send('`Input valid BrainFuck`')


def setup(bot):
    bot.add_cog(BrainFuck(bot))
