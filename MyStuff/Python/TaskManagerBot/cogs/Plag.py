import os
import requests
from discord import File
from discord.ext import commands

from lib.Translation import getModified


class Plag(commands.Cog, command_attrs=dict(help='Intento de no ser detectado por software anti-plagio lol')):
    def __init__(self, bot):
        self.bot = bot

    @staticmethod
    def getFileContent(url):
        return requests.get(url).content.decode('utf-8')

    @commands.command()
    async def plag(self, ctx):
        if len(ctx.message.content.split()) > 1:
            content = ' '.join(ctx.message.content.split()[1:])
            modifiedText = getModified(content)
            if len(modifiedText) < 1900:
                await ctx.send(f'`{modifiedText}`')
            else:
                with open('msg.txt', 'w') as f:
                    f.write(modifiedText)
                await ctx.send(file=File('msg.txt'))
                os.remove('msg.txt')

        else:
            for file in ctx.message.attachments:
                content = self.getFileContent(file.url)
                modifiedText = getModified(content)
                with open(file.filename, 'w') as f:
                    f.write(modifiedText)
                await ctx.send(file=File(file.filename))
                os.remove(file.filename)


def setup(bot):
    bot.add_cog(Plag(bot))
