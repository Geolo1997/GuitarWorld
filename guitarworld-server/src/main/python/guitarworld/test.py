from src.main.python.guitarworld.GuitarChinaCrawler import get_html, GUITAR_CHINE_URL, get_banner_item
from src.main.python.guitarworld.MySQL import clear_informations, insert_informations

if __name__ == '__main__':
    html = get_html(GUITAR_CHINE_URL)
    # print(html)
    infos = get_banner_item()
    clear_informations()
    insert_informations(infos)

