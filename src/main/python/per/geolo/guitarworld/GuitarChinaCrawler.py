import re
import urllib.request

from bs4 import BeautifulSoup

GUITAR_CHINE_URL = 'https://news.guitarchina.com'


def get_html(url):
    return urllib.request.urlopen(url).read().decode('utf-8', 'ignore')


def get_banner_item():
    html = get_html(GUITAR_CHINE_URL)
    soup = BeautifulSoup(html, "lxml")
    divs = soup.findAll('div', class_={'posts-slider-module-items', 'carousel-items', 'et_pb_slides'})
    informations = []
    for div in divs:
        articles = div.find_all('article')
        for article in articles:
            info = []
            # print(article)
            # 获取标题
            title = article.find('a').string
            info.append(title)
            # 获取图片
            image_url = re.findall('\((.*?)\)', article['style'])[0]
            info.append(image_url)
            # 获取链接
            detail_url = article.find('a')['href']
            info.append(detail_url)
            informations.append(info)

    return informations


def print_informations(infomations):
    for info in infomations:
        print(info[0], ' ', info[1], ' ', info[2])
