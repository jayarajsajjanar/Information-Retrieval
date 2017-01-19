# -*- coding: utf-8 -*-
import tweepy
import json
import datetime 
import time 


consumer_key="Knnf3EDirKpGY4xZXHmBXODUE"
consumer_secret="fhVXTyCJ6rGkLWxjqDrUqT7vbOrQr25ia56nh0946vZfdwPP17"


access_token="2850856592-KMqsJHOAo1AbqqWQ0bmgkkP2QkToPFAV4x3Q59Z"
access_token_secret="bu4QFhO6bIL1MvDDhyWJoOPQeGPkq0VY60U1cW0xJ2RHQ"

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)


api = tweepy.API(auth)

jfp = open('first_3k_demonetisation.json', 'w+')#<<<<<<-------------------------------------------
# jfp = open('usopen_geocode_trial.json', 'w+')

# keywords = ["iphone 7", "AppleEvent"] 
# keywords=["USopen", "USOpen"]
# query=["syria"]
# keywords = ["그만큼", "시리아"] 
# keywords = ["el usopen"], ["el iphone 7"]
# keywords = ["el iphone 7"] 
# keywords = ["el iphone 7"] 
# keywords=["Suriye"]
# keywords=["bir"]
# keywords= ["got", "Game of thrones"]#, "jon snow"]

# keywords =["el usopen", "el iphone 7"]
keywords = ["demonetisation"]#, "us election", "presedential elections", "donald trump"]
query = ' OR '.join(keywords)#<<<<<<-------------------------------------------

count_total_rt=0
count_tweets_collected_wo_rt=0
json_tracker=0

dump_json=[]
go_on = True
duplicate_tweeet = 0 
all_ids = []

# date_since = "2016-09-10"
# date_until = "2016-09-11"
while go_on==True:
    try:
        # print "inside while"
        total_loops = 0
        for tweet in tweepy.Cursor(api.search, 
                            q=query, #<<<<<<-------------------------------------------
                            count = 100,
                            since="2016-11-08", #<<<<<<-------------------------------------------
                            # until="2016-09-15", #<<<<<<-------------------------------------------
                            lang="en",#<<<<<<-------------------------------------------
                            # geocode = "0,0,20000km",
                            wait_on_rate_limit="True",
                            wait_on_rate_limit_notify =  "True"
                            ).items():
            # print "inside for"
            # print tweet.text
            # print tweet.created_at
            total_loops = total_loops + 1
            # print ("collecting for since : {0}....till {1}").format(date_since,date_until)

            

            if tweet.id in all_ids:
                duplicate_tweeet = duplicate_tweeet +1 
            else:
                all_ids.append(tweet.id)

                begin_loop_RT = count_total_rt

                if count_total_rt % 500==0:
                    print ("total calls made = {0} RT = {1} unRT:{2}")\
                        .format(count_tweets_collected_wo_rt+count_total_rt,count_total_rt,count_tweets_collected_wo_rt )
                    print ("date goin on {0} ").format(tweet.created_at)

                if hasattr(tweet,'retweeted_status'):
                	count_total_rt=count_total_rt+1
                	continue
                # print tweet.created_at

                # print ("collecting as well")
                dump_json.append(tweet._json)
                
                count_tweets_collected_wo_rt=count_tweets_collected_wo_rt+1

            
                if count_tweets_collected_wo_rt>=3000:#<<<<<<-------------------------------------------
                    go_on = False
                    break

                # if count_tweets_collected_wo_rt>4000 and count_tweets_collected_wo_rt<=4010:
                #     date_since = "2016-09-11"
                #     date_until = "2016-09-12"
                #     break                    
                # if count_tweets_collected_wo_rt>8000 and count_tweets_collected_wo_rt<=8010:
                #     date_since = "2016-09-12"
                #     date_until = "2016-09-13"
                #     break                

    except tweepy.TweepError, e:
        print e
        current_time = datetime.datetime.now().time()
        print ("sleeping at {0}").format(current_time)
        time.sleep(60*5)

jfp.write(json.dumps(dump_json))

# print dump_json
print ("retweets eliminated number :{0}").format(count_total_rt)
print ("tweets collected number :{0}").format(count_tweets_collected_wo_rt)
print ("total_duplicates :{0}").format(duplicate_tweeet)
print ("useful i.e. non duplicates :{0}").format(len(all_ids))
print ("total for loops : {0}").format(total_loops)

jfp.close()
# *****************************************************************************************8
