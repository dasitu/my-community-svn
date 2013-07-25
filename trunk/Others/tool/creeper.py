import urllib2
import re
import os, sys
import pickle
import mysql.connector
import time
import json
import string

iNum = 1
idex = 1

def getlongitudeLatitude(conAddress):
    city = "�ɶ���"
    api = "http://api.map.baidu.com/geocoder?address=%s&output=json&key=3a42395f4dca1f0f76e1416a99a0c2ac&city=\"%s\"" %(conAddress.decode('gbk').encode('utf-8'), city.decode('gbk').encode('utf-8'))
    api = api.replace(" ", "")
    obj = gethtml(api)
    try:
        decodejson = json.loads(obj)
    except ValueError, ex:
        print 'Catch exception, give a default value'
        decodejsonTemp = {'status': 'OK', 'result': {'precise': 1, 'confidence': 80, 'location': {'lat': 0, 'lng': 0}, 'level': u'\u9053\u8def'}}
        return decodejsonTemp
    if "location" not in decodejson['result']:
        print 'No location information, create 0 as default value'
        decodejsonTemp = {'status': 'OK', 'result': {'precise': 1, 'confidence': 80, 'location': {'lat': 0, 'lng': 0}, 'level': u'\u9053\u8def'}}
        return decodejsonTemp
    return decodejson
    
def gethtml(url):
    #opener = get_proxy_opener('http://10.159.192.62:8080', 'nsn-intra/fezhu', '5311Crocodile')
    opener = get_proxy_opener('http://10.159.32.155:8080', 'nsn-intra/fezhu', '5311Crocodile')
    fp = opener.open(url)
    return fp.read()

def getHouseNameListByOnePage(linkAdress, area, dbCursor, iPage):
    global iNum
    global idex
    print iNum

    #Get the viewSource
    viewSource = gethtml(linkAdress)
    nameList = re.findall(r'<dt><a href="http://.*"\starget=".*">(.*)</a><img src="http://.*"\s+/>.*</dt>',viewSource)
    addressList = re.findall(r'<dt><a href="http://.*"\starget=".*">.*</a><img src="http://.*"\s+/>.*</span>(.*)</dt>', viewSource)
    #nameList = re.findall(r'<dt><a href=".*"\starget=".*">(.*)</a><img src="http://.*"\s+/>.*</dt>',viewSource)
    #addressList = re.findall(r'<dt><a href=".*"\starget=".*">.*</a><img src="http://.*"\s+/>.*</span>(.*)</dt>', viewSource)
    
    for i in range(0, len(nameList)):
        print '-------------------------------------'
        print 'Page: %s' %iPage
        print 'Index: %s' %idex
        print 'Name: %s' %nameList[i]
        print 'Address: %s' %addressList[i]
        print 'Area: %s' %area

        decodeJson = getlongitudeLatitude(addressList[i])
        print 'Longitude: %f' %decodeJson['result']['location']['lng']
        print 'Latitude: %f' %decodeJson['result']['location']['lat']
        dbCursor.execute("insert into community_table values (null,'%d','%s', CURRENT_TIMESTAMP, 1, '%s', %f, %f)" %(iNum, nameList[i].decode('gbk').encode('utf-8'), addressList[i].decode('gbk').encode('utf-8'), decodeJson['result']['location']['lng'], decodeJson['result']['location']['lat']))

        # sleep 1 second to fix duplicate timestamp issue
        time.sleep(1)
        idex = idex+1
    print dbCursor
    print '------Process successfully for %s area--------' %area
    
def get_proxy_opener(proxyurl, proxyuser, proxypass, proxyscheme="http"):
   password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
   password_mgr.add_password(None, proxyurl, proxyuser, proxypass)

   proxy_handler = urllib2.ProxyHandler({proxyscheme: proxyurl})
   proxy_auth_handler = urllib2.ProxyBasicAuthHandler(password_mgr)

   return urllib2.build_opener(proxy_handler, proxy_auth_handler)

def processCreeper(cursor, db):
    global iNum
    global idex
    provName = '�Ĵ�ʡ'
    cityName = '�ɶ���'
    
    cursor.execute("insert into province_table values (1, '%s', CURRENT_TIMESTAMP)" %provName.decode('gbk').encode('utf-8'))
    cursor.execute("insert into city_table values (1,1,'%s', CURRENT_TIMESTAMP)" %cityName.decode('gbk').encode('utf-8'))
    
    # �ɻ���
    area = '�ɻ���'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 29+1):
        link = 'http://esf.cd.soufun.com/housing/133__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
    
    # ������
    idex = 1
    iNum = 2
    area = '������'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 9+1):
        link = 'http://esf.cd.soufun.com/housing/13064__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
    
    # ������
    idex = 1
    iNum = 3
    area = '������'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 28+1):
        link = 'http://esf.cd.soufun.com/housing/136__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
    
    # ��������
    idex = 1
    iNum = 4
    area = '��������'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 3+1):
        link = 'http://esf.cd.soufun.com/housing/1156__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # ������
    idex = 1
    iNum = 5
    area = '������'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 38+1):
        link = 'http://esf.cd.soufun.com/housing/130__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # ��ţ��
    idex = 1
    iNum = 6
    area = '��ţ��'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(26, 50+1):
        link = 'http://esf.cd.soufun.com/housing/131__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # ��Ȫ
    idex = 1
    iNum = 7
    area = '��Ȫ'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 15+1):
        link = 'http://esf.cd.soufun.com/housing/13065__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # ۯ��
    idex = 1
    iNum = 8
    area = 'ۯ��'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 16+1):
        link = 'http://esf.cd.soufun.com/housing/140__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # ������
    idex = 1
    iNum = 9
    area = '������'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 55+1):
        link = 'http://esf.cd.soufun.com/housing/129__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # ˫��
    idex = 1
    iNum = 10
    area = '˫��'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 28+1):
        link = 'http://esf.cd.soufun.com/housing/13066__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # ����� page 59-61 have some different format
    idex = 1
    iNum = 11
    area = '�����'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 58+1):
        link = 'http://esf.cd.soufun.com/housing/132__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # �½�
    idex = 1
    iNum = 12
    area = '�½�'
    #cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(11, 18+1):
        link = 'http://esf.cd.soufun.com/housing/939__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # �¶�
    idex = 1
    iNum = 13
    area = '�¶�'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 14+1):
        link = 'http://esf.cd.soufun.com/housing/138__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)
        db.commit()
        
    # �ɶ��ܱ�
    idex = 1
    iNum = 14
    area = '�ɶ��ܱ�'
    cursor.execute("insert into area_table values (%d, 1, '%s', CURRENT_TIMESTAMP)" %(iNum, area.decode('gbk').encode('utf-8')))
    for i in range(1, 8+1):
        link = 'http://esf.cd.soufun.com/housing/1157__0_0_0_0_%s_0_0/' %i
        getHouseNameListByOnePage(link, area, cursor, i)    
        db.commit()
        
if __name__ == "__main__":
    try:
        #Connect to DB server
        #db = mysql.connector.connect(host = 'fflrv2x.nsn-intra.net', user = 'root', passwd = 'admin', db = 'xiaoquyi', charset = 'utf8')
        db = mysql.connector.connect(host = 'localhost', user = 'root', passwd = 'root', db = 'xiaoquyi', charset = 'utf8')
        cursor = db.cursor()
        processCreeper(cursor, db)
        db.commit()
        cursor.close()
        db.close()
    except mysql.connector.Error as err:
        print("Something went wrong: {}".format(err))
        cursor.close()
        db.close()
    
    


    
