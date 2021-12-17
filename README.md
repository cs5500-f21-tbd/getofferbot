

# GetOfferBot

## Introduction

Computer science and software engineering are exciting fields of work and study; as a result, there are various new and existing opportunities for college sophomores, master's program graduates, experienced software engineers, and everyone in between. Nowadays, job information in the field of software engineering is scattered across the Internet, as well as employment services (LinkedIn, Indeed, etc), emails, career fairs, etc. Sometimes a job seeker may even have to go to each of the company's websites in order to find the jobs they are interested in. There is not a unified way of seeing all available jobs on the market, much less filtering and sorting these jobs to your liking.

GetOfferBot is a Discord bot that allows users to query for available job opportunities on the market. It enables Discord users to interact with an online bot powered by [Fly.io]([https://fly.io/](https://fly.io/)) to retrieve job information in a dedicated Discord channel. An offline scraper will be running and refreshing job information in the database on a daily basis. Beyond simply listing all jobs, the bot can also filter or sort the jobs based on type, company, location, salary and even whether they sponsor a work visa. This is also accompanied by an existing feature to view recommended jobs that are trending on LinkedIn or Indeed. This wiki will walk you through how to set up the bot as a developer, and how to use this bot on Discord as a user.

## Contributors

| Github Alias | Name | Email Address |
| ------------- | ----------- | ---------------------------- |
| luckyfantuan | Lili Qing | [qing.l@northeastern.edu](mailto:qing.l@northeastern.edu) |
| Poppymeow | Hui Jiang | [jiang.hui1@northeastern.edu](mailto:jiang.hui1@northeastern.edu) |
| Magnus321321 | Jinhong Li | [li.jinh@northeastern.edu](mailto:li.jinh@northeastern.edu) |
| xxjingxx | Jing Xie | [xie.jing@northeastern.edu](mailto:xie.jing@northeastern.edu) |
| yiranwangg | Yiran Wang | [wang.yiran@northeastern.edu](mailto:wang.yiran@northeastern.edu) |
| yiyuzhang0604 | Yiyu Zhang | [zhang.yiyu1@northeastern.edu](mailto:zhang.yiyu1@northeastern.edu) |
| pxie2016 | Zimeng Xie | [xie.zim@northeastern.edu](mailto:xie.zim@northeastern.edu) |

## Documents
[Team Document Folder](https://drive.google.com/drive/folders/1qumKav0J6wYlSWN20D1wMoB0DpaXbNnA?usp=sharing)

[Design Document](https://docs.google.com/document/d/1qZTnP_CzsbbyK67JpjvUq0xAGtNxPXyEOdnjc8I90ak/edit?usp=sharing)

[Final Presentation](https://www.youtube.com/watch?v=VIAjotHrMos)

## Developer Instructions

### Setup Discord
Follow the instructions [here](https://www.writebots.com/discord-bot-token/) to set up a Discord application, create a bot, add bot to your server, and obtain a bot token. Record the token somewhere (such as copying, *Ctrl/Cmd+C*), as you will need it in the next step.

[Discord invite link](https://discord.gg/2qs8GDVZmH)

### Setup MongoDB
Refer to this [link](https://docs.google.com/document/d/1csfXK2iwvSoEUhY86kXhekMM3bHfVUiO0Kd6eZ-s4Os/edit?usp=sharing) for the MongoDB setup instructions.

## Bot Installation

1. Pull the git repository into the local machine.

```bash
git clone https://github.com/cs5500-f21-tbd/getofferbot
```
2. Set up environment variables
```
flyctl secrets set FLY_API_TOKEN={Calculated by the permission calculator in Discord developer portal}
flyctl secrets set MONGODB_URI={Mongo DB URI from Setup MongoDB step}
flyctl secrets set BOT_TOKEN={Bot token from Setup Discord step}
```
3. Deploy with Fly
```bash
flyctl deploy
```
4. Test in Discord with commands provided in the **User Manual** below.

## User Manual

### Search for 10 most recent SDE jobs
```
/search
```
### Query for jobs with filters
Filter Conditions
| Condition Name | Description | Required | Sample Values |
| ----------- | ------------- | -------- | -------|
| title | The title of a job posting. | No | Software Engineer |
| type | The type of a job posting. | No | Full-time/Part-time |
| company | The name of the company associated with a job posting. | No | Amazon |
| location | The location of a job posting. | No | Seattle |
| timeposted | The time when a job posting is posted. Optional argument defaults to filtering jobs less than X days old. | No | 1 day |
| rating | The rating of the company associated with a job posting. Optional argument defaults to filtering jobs with star ratings greater than X stars. | No | 4.0 |
| annualpay | The annual salary of a job posting. Optional argument defaults to filtering jobs with annual salary greater than X US dollars. | No | 50000 |
| visa | The visa sponsorship possibility of a job posting. | No | Yes |

Example Command
```
/filter --title Software Engineer --type Full-time --company Amazon
```
### Sort the jobs based on a job attribute
Sort Conditions
| Condition Name | Description | Required | Order Options |
| ----------- | ------------- | -------- | -------|
| salary | Sort jobs by the lower salary bound, set default order as descending. | No | asc / desc |
| rating | Sort jobs based on the star rating in descending order, set default order as descending. | No | asc / desc |
| location | Sort jobs based on the distance between user and jobs, set default order as ascending. | No | asc / desc |
| time | Sort jobs based on post time, default order: newest posted first. | No | old / new |
Example Command
```
/sort --salary --desc
```
### Get help with available commands
```
/help
```

# Code Coverage Goals
We are focusing on achieving 70% branch coverage.

# Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

# License
[MIT]([https://choosealicense.com/licenses/mit/](https://choosealicense.com/licenses/mit/))
