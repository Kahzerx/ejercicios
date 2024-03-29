#!/usr/bin/python3
from dotenv import load_dotenv
import os
from discord.ext import commands

from lib.Databases import tryCreateDatabase

bot = commands.Bot(command_prefix='.')
# bot.remove_command('help')


@bot.command(hidden=True)
async def reload(ctx):
    [bot.unload_extension(i) for i in [extension[0] for extension in bot.extensions.items()]]
    register_commands()
    await ctx.send('Reloaded!')


def register_commands():
    [bot.load_extension(f'cogs.{file[:-3]}') for file in os.listdir('./cogs') if file.endswith('.py')]


if __name__ == '__main__':
    load_dotenv()

    tryCreateDatabase()
    register_commands()

    bot.run(os.getenv('TOKEN'))
