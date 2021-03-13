#!/usr/bin/python3
from dotenv import load_dotenv
import os
from discord.ext import commands
from discord import Status

bot = commands.Bot(command_prefix='.')

@bot.event
async def on_ready():
    await bot.change_presence(status=Status.dnd)

if __name__ == "__main__":
    load_dotenv()
    bot.run(os.getenv('TOKEN'), bot=False)