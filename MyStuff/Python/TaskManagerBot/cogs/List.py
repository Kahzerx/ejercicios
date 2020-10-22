from discord.ext import commands
from discord import Embed, Color
from lib.QueryDB import getTodoTasks, getDoneTasks, getAllTasks


class List(commands.Cog, command_attrs=dict(help='List tasks')):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def list(self, ctx):
        tasks = getTodoTasks(ctx.message.author.id)
        embed = Embed(
            title='Tasks:',
            description=tasks,
            color=Color.green() if tasks != 'No tasks' else Color.red()
        )
        await ctx.send(embed=embed)

    @commands.command()
    async def listDone(self, ctx):
        tasks = getDoneTasks(ctx.message.author.id)
        embed = Embed(
            title='All Tasks:',
            description=tasks,
            color=Color.green() if tasks != 'No tasks' else Color.red()
        )
        await ctx.send(embed=embed)

    @commands.command()
    async def listAll(self, ctx):
        tasks = getAllTasks(ctx.message.author.id)
        embed = Embed(
            title='All Tasks:',
            description=tasks,
            color=Color.green() if tasks != 'No tasks' else Color.red()
        )
        await ctx.send(embed=embed)


def setup(bot):
    bot.add_cog(List(bot))
