import time
import json
import datetime
import re
import nltk 

new_all = [] 

inf=open('first_3k_demonetisation.json','r')#<<<<<------------------------------------------------
all_tweets = json.loads(inf.read())



count_processed_tweets = 0 
for each_tweet in all_tweets:

	# count_processed_tweets = count_processed_tweets + 1
	# print(count_processed_tweets), #end=" ")
	
	# print each_tweet
	fresh_tweet={}
	fresh_tweet['id']=each_tweet['id']
	# fresh_tweet['topic']="tech"#<<<<<------------------------------------------------
	fresh_tweet['tweet_text']=each_tweet['text']
	# print type(each_tweet['text'])
	# fresh_tweet['tweet_lang']=each_tweet['lang']
	# *******************************************************
	#stopwords remove
	stopwords = "a's, able, about, above, abroad, according, accordingly, across, actually, adj, after, afterwards, again, against, ago, ahead, ain't, aint, all, allow, allows, almost, alone, along, alongside, already, also, although, always, am, amid, amidst, among, amongst, amoungst, amount, an, and, another, any, anybody, anyhow, anyone, anything, anyway, anyways, anywhere, apart, appear, appreciate, appropriate, are, aren't, arent, around, as, aside, ask, asking, associated, at, available, away, awfully, back, backward, backwards, be, became, because, become, becomes, becoming, been, before, beforehand, begin, behind, being, believe, below, beside, besides, best, better, between, beyond, bill, both, bottom, brief, but, by, c'mon, c's, call, came, can, can't, cannot, cant, caption, cause, causes, certain, certainly, changes, clearly, cmon, co, co., com, come, comes, computer, con, concerning, consequently, consider, considering, contain, containing, contains, corresponding, could, couldn't, couldnt, course, cry, currently, dare, daren't, darent, de, definitely, describe, described, despite, detail, did, didn't, didnt, different, directly, do, does, doesn't, doesnt, doing, don't, done, dont, down, downwards, due, during, each, edu, eg, eight, eighty, either, eleven, else, elsewhere, empty, end, ending, enough, entirely, especially, et, etc, even, ever, evermore, every, everybody, everyone, everything, everywhere, ex, exactly, example, except,fairly, far, farther, few, fewer, fifteen, fifth, fify, fill, find, fire, first, five, followed, following, follows, for, forever, former, formerly, forth, forty, forward, found, four, from, front, full, further, furthermore, get, gets, getting, give, given, gives, go, goes, going, gone, got, gotten, greetings, had, hadn't, hadnt, half, happens, hardly, has, hasn't, hasnt, have, haven't, havent, having, he, he'd, he'll, he's, hello, help, hence, her, here, here's, hereafter, hereby, herein, heres, hereupon, hers, herse, herself, hi, him, himse, himself, his, hither, hopefully, how, how's, howbeit, however, hows, hundred,i'd, i'll, i'm, i've, ie, if, ignored, immediate, in, inasmuch, inc, inc., indeed, indicate, indicated, indicates, inner, inside, insofar, instead, interest, into, inward, is, isn't, isnt, it, it'd, it'll, it's, itd, itll, its, itse, itself, just, keep, keeps, kept, know, known, knows, last, lately, later, latter, latterly, least, less, lest, let, let's, lets, like, liked, likely, likewise, little, look, looking, looks, low, lower, ltd, made, mainly, make, makes, many, may, maybe, mayn't, maynt, me, mean, meantime, meanwhile, merely, might, mightn't, mightnt, mill, mine, minus, miss, more, moreover, most, mostly, move, mr, mrs, much, must, mustn't, mustnt, my, myse, myself, name, namely, nd, near, nearly, necessary, need, needn't, neednt, needs, neither, never, neverf, neverless, nevertheless, new, next, nine, ninety, no, no-one, nobody, non, none, nonetheless, noone, nor, normally, not, nothing, notwithstanding, novel, now, nowhere, obviously, of, off, often, oh, ok, okay, old, on, once, one, one's, ones, only, onto, opposite, or, other, others, otherwise, ought, oughtn't, oughtnt, our, ours, ourselves, out, outside, over, overall, own, part, particular, particularly, past, per, perhaps, placed, please, plus, possible, presumably, probably, provided, provides, put, que, quite, qv, rather, rd, re, really, reasonably, recent, recently, regarding, regardless, regards, relatively, respectively, right, round, said, same, saw, say, saying, says, second, secondly, see, seeing, seem, seemed, seeming, seems, seen, self, selves, sensible, sent, serious, seriously, seven, several, shall, shan't, shant, she, she'd, she'll, she's, shes, should, shouldn't, shouldnt, show, side, since, sincere, six, sixty, so, some, somebody, someday, somehow, someone, something, sometime, sometimes, somewhat, somewhere, soon, sorry, specified, specify, specifying, still, sub, such, sup, sure, system, t's, take, taken, taking, tell, ten, tends, than, thank, thanks, thanx, that, that'll, that's, that've, thatll, thats, thatve, the, their, theirs, them, themselves, then, thence, there, there'd, there'll, there're, there's, there've, thereafter, thereby, thered, therefore, therein, therell, therere, theres, thereupon, thereve, these, they, they'd, they'll, they're, they've, theyd, theyll, theyre, theyve, thick, thin, thing, things, think, third, thirty, this, thorough, thoroughly, those, though, three, through, throughout, thru, thus, till, to, together, too, took, top, toward, towards, tried, tries, truly, try, trying, twelve, twenty, twice, two, under, underneath, undoing, unfortunately, unless, unlike, unlikely, until, unto, up, upon, upwards, us, use, used, useful, uses, using, usually, value, various, versus, very, via, viz, vs, want, wants, was, wasn't, wasnt, way, we, we'd, we'll, we're, we've, welcome, well, went, were, weren't, werent, weve, what, what'll, what's, what've, whatever, whatll, whats, whatve, when, when's, whence, whenever, whens, where, where's, whereafter, whereas, whereby, wherein, wheres, whereupon, wherever, whether, which, whichever, while, whilst, whither, who, who'd, who'll, who's, whod, whoever, whole, wholl, whom, whomever, whos, whose, why, why's, whys, will, willing, wish, with, within, without, won't, wonder, wont, would, wouldn't, wouldnt, yes, yet, you, you'd, you'll, you're, you've, youd, youll, your, youre, yours, yourself, yourselves, youve, zero"
	stopwords_list = stopwords.split(',')

	combined1 = "(" + "\s)|(".join(stopwords_list[:99]) + "\s)"  
	combined2 = "(" + "\s)|(".join(stopwords_list[99:198]) + "\s)"  
	combined3 = "(" + "\s)|(".join(stopwords_list[198:297]) + "\s)"  
	combined4 = "(" + "\s)|(".join(stopwords_list[297:396]) + "\s)"  
	combined5 = "(" + "\s)|(".join(stopwords_list[396:495]) + "\s)"  
	combined6 = "(" + "\s)|(".join(stopwords_list[495:594]) + "\s)"  
	combined7 = "(" + "\s)|(".join(stopwords_list[594:693]) + "\s)"  
	combined8 = "(" + "\s)|(".join(stopwords_list[693:]) + "\s)"  

	temp1=each_tweet['text']
	temp2 = (re.sub(combined1,r' ',temp1,flags=re.IGNORECASE))
	temp3 = (re.sub(combined2,r' ',temp2,flags=re.IGNORECASE))
	temp4 =(re.sub(combined3,r' ',temp3,flags=re.IGNORECASE))
	temp5 =(re.sub(combined4,r' ',temp4,flags=re.IGNORECASE))
	temp6 =(re.sub(combined5,r' ',temp5,flags=re.IGNORECASE))
	temp7 =(re.sub(combined6,r' ',temp6,flags=re.IGNORECASE))
	temp8 =(re.sub(combined7,r' ',temp7,flags=re.IGNORECASE))
	temp9 =(re.sub(combined8,r' ',temp8,flags=re.IGNORECASE))
	temp10 =(re.sub("(\sa\s)|(\sI\s)|(\sD\s)|(\sU\s)|(\sZ\s)|(^a\s)|(^I\s)|(^D\s)|(^U\s)|(^Z\s)",r' ',temp9,flags=re.IGNORECASE))
	temp11 =(re.sub('^[\w\d\s]',r'',temp10,flags=re.IGNORECASE))
	temp12 =(re.sub('[^a-zA-z0-9\s]*',r'',temp11,flags=re.IGNORECASE))
	
	#remove special chars
	fresh_tweet['text_en'] = temp12
	# print temp12

	# print type(temp12)
	# ******************************************************8**
	# # fresh_tweet['tweet_en']="need to process"
	# fresh_tweet['text_es']=None
	# fresh_tweet['text_tr']=None
	# fresh_tweet['text_ko']=None

	# temp_hashtags = []
	# for all_mentions in each_tweet['entities']['user_mentions'] :
	# 	temp_ments.append(all_mentions['screen_name'])
	 
	text=nltk.word_tokenize(temp12)
	aa =  nltk.pos_tag(text)
	# aa =  nltk.pos_tag(str(temp12))
	# print aa
	# time.sleep(10)
	prp=[]
	vbp=[]
	vbg=[]
	inn=[]
	cc =[]
	vbz=[]
	nn =[]
	for each_word in (aa):
		if (each_word[1]=='PRP'):
			prp.append(each_word[0])
		if (each_word[1]=='NN' or each_word[1]=='NNP'):
			nn.append(each_word[0])
		if (each_word[1]=='VBP'):
			vbp.append(each_word[0])
		if (each_word[1]=='VBG'):
			vbg.append(each_word[0])
		if (each_word[1]=='IN'):
			inn.append(each_word[0])
		if (each_word[1]=='CC'):
			cc.append(each_word[0])
		if (each_word[1]=='VBZ'):
			vbz.append(each_word[0])

	fresh_tweet['prp']=prp
	fresh_tweet['vbp']=vbp
	fresh_tweet['vbg']=vbg
	fresh_tweet['inn']=inn
	fresh_tweet['cc']=cc
	fresh_tweet['vbz']=vbz
	fresh_tweet['nn']=nn
	# print fresh_tweet['prp'],fresh_tweet['vbp'],fresh_tweet['vbg'],fresh_tweet['inn'],fresh_tweet['cc'],fresh_tweet['vbz'],fresh_tweet['nn']

	temp_hashtags = []
		
	if each_tweet['entities']['hashtags']:
    		for bb in each_tweet['entities']['hashtags']:
	        	temp_hashtags.append(bb['text'])

	fresh_tweet['hashtags']=temp_hashtags

	# temp_ments = []
	# for all_mentions in each_tweet['entities']['user_mentions'] :
	# 	temp_ments.append(all_mentions['screen_name'])
	# fresh_tweet['mentions']=temp_ments

	# temp_url = []
	# for aa in each_tweet['entities']['urls']:
	#     temp_url.append(aa['url'])
	# fresh_tweet['tweet_urls']=temp_url
	
	temp_emoticons = []
	emoji_pattern = re.compile("["
	        u"\U0001F600-\U0001F64F"  # emoticons
	        u"\U0001F300-\U0001F5FF"  # symbols & pictographs
	        u"\U0001F680-\U0001F6FF"  # transport & map symbols
	        u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
	                           "]+", flags=re.UNICODE)
	for xxx in emoji_pattern.finditer(each_tweet['text'],re.S):
		temp_emoticons.append(xxx.group(0))
	fresh_tweet['tweet_emoticons']=temp_emoticons

	# fresh_tweet['emoticons']="need to process"
	temp_tweet_date = time.strftime('%Y-%m-%dT%H:00:00Z', time.strptime(each_tweet['created_at'],'%a %b %d %H:%M:%S +0000 %Y'))
	fresh_tweet['tweet_date']=temp_tweet_date

	if each_tweet['coordinates']: fresh_tweet['tweet_loc']=each_tweet['coordinates']['coordinates']
	else: fresh_tweet['tweet_loc']=[]

	new_all.append(fresh_tweet)
	# time.sleep(10)
inf.close()

jfp = open('first_3k_demonetisation_processed.json', 'w+')#<<<<<------------------------------------------------
jfp.write(json.dumps(new_all))
jfp.close()

# jfpp=open('day101112_apple_12500_4000pd_processed.json', 'r')#<<<<<------------------------------------------------
# pp = json.loads(jfpp.read())

# # new_all_json_encoded = json.dumps(new_all)

# # df = json.loads(new_all_json_encoded)

# print ("\n")
# print len(pp)
# for aa in pp[:10]:
# 	print aa['tweet_text']
# 	print aa['text_en']
# # print df[0]
# print pp[0]

# jfpp.close()

def extract_entities(text):
     for sent in nltk.sent_tokenize(text):
         for chunk in nltk.ne_chunk(nltk.pos_tag(nltk.word_tokenize(sent))):
         	print chunk.node
             if hasattr(chunk, 'node'):
                 print chunk.node, ' '.join(c[0] for c in chunk.leaves())