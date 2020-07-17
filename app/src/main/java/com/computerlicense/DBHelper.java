package com.computerlicense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;

public class DBHelper extends SQLiteOpenHelper {

    private final String ID="id";
    private static final String DATABASE_NAME="Content.db";
    private final String TABLE_NAME="Table_content";
    private final String INFO="info";
    private final String CONTENT="content";
    private final String ISLIKE="islike";
    private final String KEYWORD="keyword";
    public static final int DATABASE_VERSION=1;

    private final String createQuery="CREATE TABLE IF NOT EXISTS "+TABLE_NAME
            +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +INFO+" INTEGER, "
            +CONTENT+" TEXT, "
            +ISLIKE+" INTEGER, "
            +KEYWORD+" TEXT)";

//생성자
    public DBHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    데이터 베이스 생성
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createQuery);
    }

//    데이터베이스 업데이트
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor loadSQLiteDBCursor(boolean is1, boolean is2, boolean is3){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String selectQuery="";

        if(is1==true){
            if(is2==true){
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+" ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=3 ORDER BY RANDOM() LIMIT 1";
            }
            else{
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=2 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =1 ORDER BY RANDOM() LIMIT 1";
            }
        }
        else{
            if(is2==true){
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=1 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =2 ORDER BY RANDOM() LIMIT 1";
            }
            else{
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =3 ORDER BY RANDOM() LIMIT 1";
//                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+" ORDER BY RANDOM() LIMIT 1";
            }
        }

        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

    public Cursor loadSQLiteDBCursor_liked(boolean is1, boolean is2, boolean is3){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String selectQuery="";

        if(is1==true){
            if(is2==true){
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+" WHERE "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=3 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
            }
            else{
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=2 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =1 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
            }
        }
        else{
            if(is2==true){
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=1 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =2 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
            }
            else{
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =3 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
//                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+" WHERE "+ISLIKE+ " =1 ORDER BY RANDOM() LIMIT 1";
            }
        }

        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

    public Cursor getCountLikeCursor(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String countQuery="SELECT * FROM "+ TABLE_NAME+" WHERE ISLIKE=1";
        Cursor cursor=null;
        try{
            cursor=db.rawQuery(countQuery, null);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

//데이터베이스에 값 삽입
    public void insertContent(int info, String content, int islike, String keyword){
        ContentValues contentValues=new ContentValues();
        SQLiteDatabase db=getWritableDatabase(); // 데이터베이스를 수정할 때는 getWritableDatabase(), 데이터를 읽을 떄는 getReadableDatabase()를 씁니다
        contentValues.put(INFO, info);
        contentValues.put(CONTENT, content);
        contentValues.put(ISLIKE, islike);
        contentValues.put(KEYWORD, keyword);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void loadContent(SQLiteDatabase db){
        insertContent(1, "플러그 앤 플레이: 한글 Windows에 하드웨어 장치를 추가할 때 운영체제가 이를 자동으로 인식하여 설치 및 환경설정을 용이하게 해주는 기능", 0, "플러그 앤 플레이");
        insertContent(1, "단축키 F3: 탐색기의 '검색 상자' 선택하기", 0, "F3");
        insertContent(1, "단축키 Alt+Esc: 실행 중인 프로그램 사이에 작업 전환을 한다.\n한 번씩 누를 떄마다 열려 있는 프로그램의 창이 바로 바뀐다.", 0, "Alt+Esc");
        insertContent(2, "현재 워크시트의 왼쪽에 새로운 시트를 삽입할 때에는 Shift+F11을 누른다.", 0, "Shift+F11");
        insertContent(3, "데이터베이스 방식은 데이터의 중복을 최소화할 수 있다.", 0, "데이터베이스 방식");
        insertContent(3, "유효성 검사 규칙: 테이블에 잘못된 데이터가 입력되면 이후 많은 문제가 발생하는 문제를 해결하기 위한 방안. 점검을 필요하는 필드에 요구 사항이나 조건 또는 입력이 가능한 데이터 등을 미리 지정한 후 데이터 입력 시 이를 점검하도록 하는 기능.", 0, "유효성 검사 규칙");
        insertContent(3, "폼은 바운드(Boung) 폼과 언바운드(Unbound) 폼이 있다.", 0, "폼");
        insertContent(1, "단축키: Windows 로고 키+M: 열려 있는 모든 창 최소화하기", 0, "단축키");
        insertContent(1, "한글 Windows의 바탕 화면 배경으로 사용할 수 있는 파일의 확장자 형식에는 jpg, bmp, png 등의 이미지 형식이 있습니다", 0, "jpg, bmp, png");
        insertContent(1, "작업 표시줄 위치로 마우스를 가져오면 작업 표시줄이 나타나고, 다른 곳으로 이동하면 작업 표시줄이 사라지도록 설정할 수 있다.", 0, null);
        insertContent(1, "작업 표시줄의 오른쪽 끝에 있는 [바탕 화면 보기] 단추 위로 마우스 포인터를 이동하면 열려 있는 모든 창이 투명해져 바탕 화면이 표시되도록 설정할 수 있다.", 0, "[바탕 화면 보기]");
        insertContent(1, "컴퓨터 부팅 후, Windows가 실행(Start-up)될 때 자동으로 프로그램이 실행되도록 하려면 실행 프로그램의 아이콘을 시작 프로그램 폴더에 복사해 두면 된다.", 0, null);
        insertContent(1, "시작 메뉴는 작업 표시줄의 가장 왼쪽에 있는 단추를 눌렀을 때 표시된다.", 0, "시작 메뉴");
        insertContent(1, "시작 메뉴에 있는 프로그램의 바로 가기 목록을 삭제하여도 실제 프로그램은 삭제되지 않는다.", 0, "바로 가기 목록");
        insertContent(1, "탐색 창에서 폴더를 선택한 후에 왼쪽 방향키(←)를 누르면 선택된 폴더가 열려 있을 때는 닫고, 닫혀 있으면 상위 폴더가 선택된다.", 0, null);
        insertContent(1, "내 컴퓨터에 연결되어 있는 프린터를 다른 컴퓨터에서 사용할 수 있도록 프린터 공유가 가능하다.", 0, "프린터 공유");
        insertContent(1, "한글 Windows에서의 자르기, 복사, 붙여넣기(Cut, Copy, Paste)에서 선택된 데이터는 자르기 혹은 복사 작업을 통하여 임시 기억공간인 클립보드로 들어가게 된다.", 0, "자르기 복사, 붙여넣기(Cut, Copy, Paste)");
        insertContent(1, "삭제할 파일을 선택한 다음 Shift와 Delete를 함께 누르면 휴지통에 저장되지 않고 영구히 삭제된다.", 0, "Shift와 Delete");
        insertContent(1, "하드디스크 드라이브마다 휴지통 크기를 다르게 설정할 수 있다.", 0, null);
        insertContent(1, "휴지통에 보관된 실행 파일은 복원은 가능하지만 휴지통에서 실행하거나 이름을 변경할 수는 없다.", 0, "휴지통");
        insertContent(1, "[명령 프롬프트]는 ‘실행 창’에 ‘cmd’를 입력한 후 Enter를 누르면 실행된다.", 0, "[명령 프롬프트]");
        insertContent(1, "[명령 프롬프트] 창에서 ‘exit’을 입력하여 종료할 수 있다.", 0, "[명령 프롬프트]");
        insertContent(1, "한글 Windows의 메모장에서 작성한 문서를 저장할 때 기본 저장 파일의 확장자는 .txt이다.", 0, "메모장");
        insertContent(1, "바탕 화면의 바로 가기 메뉴 [개인 설정]을 선택하여 화면 보호기를 설정할 수 있다.", 0, null);
        insertContent(1, "연결 프로그램이 지정되지 않았을 경우 데이터 파일을 더블클릭하면 연결 프로그램을 선택하기 위한 대화상자가 표시된다.", 0, null);
        insertContent(1, "디바이스 드라이버: 한글 Windows에서 새 하드웨어를 추가할 때 새로운 장치를 인식하게 하기 위해 설치된 장치에 설치되어야하는 프로그램", 0, "디바이스 드라이버");
        insertContent(1, "플러그 앤 플레이가 지원되는 하드웨어를 장착하고 Windows를 실행하면 새로 장착한 하드웨어를 자동으로 인식하고 설치한다.", 0, "플러그 앤 플레이");
        insertContent(1, "[시스템] 창에서 컴퓨터 이름을 변경하려면 관리자 계정이 필요하다.", 0, "[시스템]");
        insertContent(1, "[장치 관리자] 창에서는 장치들의 드라이버를 식별하고, 설치된 장치 드라이버에 대한 정보를 알 수 있다.", 0, "[장치 관리자]");
        insertContent(1, "글꼴을 추가하려면 설치할 글꼴의 바로 가기 메뉴에서 ‘설치’를 선택한다.", 0, "글꼴");
        insertContent(1, "기본 프린터로 설정된 프린터도 삭제할 수 있다.", 0, "기본 프린터");
        insertContent(1, "한 대의 컴퓨터에 여러 개의 로컬 프린터를 설치할 수 있으며, 한 대의 프린터를 네트워크로 공유하여 여러 대의 컴퓨터에서 사용할 수 있다.", 0, null);
        insertContent(1, "한 대의 프린터를 가지고 여러 대의 PC에서 프린트를 하려면 아래의 프린터 공유 방법을 따라야 한다. \n[제어판]→[장치 및 프린터] →[프린터 선택] →[프린터 속성]", 0, null);
        insertContent(1, "’디스크 조각 모음[드라이브 조각 모음 및 최적화]’는 하드디스크의 조각난 파일과 폴더를 인접한 공간을 차지하도록 통합한다.", 0, "디스크 조각 모음[드라이브 조각 모음 및 최적화]");
        insertContent(1, "레지스트리는 윈도우가 작동하는 데 필요한 수많은 정보들을 저장하고 있다.", 0, "레지스트리");
        insertContent(1, "Windows의 방화벽은 특정 프로그램에 대하여 방화벽을 통해 통신하도록 허용할 수 있다.", 0, "방화벽");
        insertContent(1, "바이러스 백신은 방화벽 기능을 가지고 있기 않기 때문에 백신을 설치한 경우라도 방화벽은 필요하다.", 0, null);
        insertContent(1, "ipconfig를 이용하여 네트워크 설정에 관한 정보를 얻을 수 있다.", 0, "ipconfig");
        insertContent(1, "ping을 이용하여 자신의 네트워크 카드가 정상적으로 작동하는지 알 수 있다.", 0, "ping");
        insertContent(1, "DHCP 서버: 컴퓨터에 IP 어드레스를 자동으로 할당해 주는 기능을 수행한다.", 0, "DHCP 서버");
        insertContent(1, "IPv4 주소는 인터넷에 연결된 호스트 컴퓨터의 유일한 주소로, 네트워크 주소와 호스트 주소로 구성되며, 32Bit 주소를 8비트씩 점(.)으로 구분한다.", 0, "IPv4 주소");
        insertContent(1, "게이트웨이는 다른 네트워크와의 데이터 교환을 위한 출입구 역할을 하는 장치이다.", 0, "게이트웨이");
        insertContent(1, "TCP/IP 프로토콜 설정에 있어 서브넷 마스크(Subnet Mask)는 네트워크 ID 부분과 호스트 ID 부분을 구별해주는 역할을 한다.", 0, "서브넷 마스크(Subnet Mask)");
        insertContent(1, "펌웨어: 하드웨어의 동작을 지시하는 소프트웨어이지만 하드웨어적으로 구성되어 하드웨어의 일부분으로도 볼 수 있다.", 0, "펌웨어");
        insertContent(1, "펌웨어는 하드웨어 교체 없이 소프트웨어 업그레이드만으로 시스템의 성능을 높이기 위한 목적으로 사용되며 하드웨어와 소프트웨어의 중간에 해당한다.", 0, "펌웨어");
        insertContent(1, "아날로그 신호는 시간에 따라 크기가 연속적으로 변하는 정보를 말한다.", 0, "아날로그 신호");
        insertContent(1, "디지털 신호는 시간에 따라 이산적으로 변하는 정보를 말한다.", 0, "디지털 신호");
        insertContent(1, "보수(Complement)는 컴퓨터 연산에서 덧셈 연산을 이용하여 뺄셈을 수행하기 위해 사용한다.", 0, "보수(Complement)");
        insertContent(1, "전송 오류 검출 방식에는 CRC 방식, 패리티 검사 방식, 정마크 부호 방식 등이 있다.", 0, "CRC 방식, 패리티 검사 방식, 정마크 부호 방식");
        insertContent(1, "ASCII 코드: 미국표준협회에서 제정한 데이터 통신에 널리 사용하기 위한 정보 교환용 코드, 한 문자를 표현하는 데 7개의 데이터 비트(Data Bit)와 한 개의 패리티 비트(Parity Bit) 사용, 128개의 문자 코드가 정의되어 있음", 0, "ASCII 코드");
        insertContent(1, "BCD 코드: 64가지 문자를 표현할 수 있으나 영문 소문자는 표현이 불가능하다.", 0, "BCD 코드");
        insertContent(1, "EBCDIC 코드: BCD 코드를 확장한 코드 체계로 256가지의 문자를 표현할 수 있다.", 0, "EBCDIC 코드");
        insertContent(1, "유니코드(Unicode): 세계 각국의 언어를 통일된 방법으로 표현할 수 있게 제안된 국제적인 코드 규약의 이름이다.", 0, "유니코드(Unicode)");
        insertContent(1, "레지스터는 CPU 내부에 처리할 명령어나 연산의 중간 값 등을 일시적으로 저장하는 기억장치이다.", 0, "레지스터");
        insertContent(1, "프로그램 카운터(PC): 컴퓨터 CPU 내에서 다음에 실행할 명령어 주소를 가리키는 레지스터", 0, "프로그램 카운터(PC)");
        insertContent(1, "CPU가 기본적으로 클럭 주기에 따라 명령을 수행한다고 할 때, 이 클럭 값이 높을수록 CPU는 빠르게 일을 하고 있는 것으로 볼 수 있다.", 0, "클럭 값이 높을수록 CPU는 빠르게 일");
        insertContent(1, "클럭 주파수를 높이기 위해 메인보드로 공급되는 클럭을 CPU 내부에서 두 배로 증가시켜 사용하는 클럭 더블링(Clock Doubling)이란 기술이 486 이후부터 사용되었다.", 0, "클럭 더블링(Clock Doubling)");
        insertContent(1, "하드 디스크 인터페이스 방식은 EIDE, SATA, SCSI 방식 등이 있다.", 0, "EIDE, SATA, SCSI");
        insertContent(1, "캐시(Cache) 메모리는 CPU와 주기억장치 사이에 위치하여 두 장치간의 속도 차이를 줄여 컴퓨터의 처리속도를 빠르게 하기 위한 메모리이다.", 0, "캐시(Cache) 메모리");
        insertContent(1, "플래시(Flash) 메모리는 비휘발성 기억장치로 주로 디지털 카메라나 MP3, 개인용 정보 단말기, USB 드라이브 등 휴대형 기기에서 대용량 정보를 저장하는 용도로 사용된다.", 0, "플래시(Flash) 메모리");
        insertContent(1, "운영체제에서 관리하는 가상 메모리는 실제로 하드디스크 장치에 존재한다.", 0, "가상 메모리");
        insertContent(1, "플래시 메모리(Flash Memory)는 전원이 공급되지 않아도 내용이 지워지지 않는 비휘발성 메모리인 EEPROM의 일종으로 휴대용 컴퓨터나 디지털 카메라 등의 보조기억장치로 이용된다.", 0, "플래시 메모리(Flash Memory)");
        insertContent(1, "컴퓨터의 계산 속도 단위를 느린 것부터 빠른 것까지 순서대로 나타내면 아래와 같다.\nms→μs→ns→ps", 0, "ms→μs→ns→ps");
        insertContent(1, "SSD는 Solid State Drive(또는 Disk)의 약자로 HDD에 비해 속도가 빠르고, 발열 및 소음이 적으며, 소형화, 경령화 할 수 있는 장점이 있다.", 0, "SSD");
        insertContent(1, "SSD는 HDD에 비해 외부의 충격에 강하며, 디스크가 아닌 메모리에 데이터를 기록하므로 배드섹터가 발생하지 않는다.", 0, "SSD");
        insertContent(1, "픽셀(Pixel): 화면을 이루는 최소의 단위로서 그림의 화소라는 뜻을 의미하며 픽셀 수가 많을수록 해상도가 높아진다.", 0, "픽셀(Pixel)");
        insertContent(1, "채널 제어장치는 중앙처리장치와 입·출력장치 사이의 속도 차이로 인한 문제점을 해결해준다.", 0, "채널 제어장치");
        insertContent(1, "인터럽트(Interrupt): 컴퓨터에서 정상적인 프로그램을 처리하고 있는 도중에 특수한 상태가 발생했을 때 현재 실행하고 있는 프로그램을 일시 중단하고, 그 특수한 상태를 처히나 후 다시 원래의 프로그램을 처리하는 과정", 0, "인터럽트(Interrupt)");
        insertContent(1, "CISC는 RISC에 비교해서 전력 소모가 많으며 처리 속도가 느리다.", 0, "CISC");
        insertContent(1, "RISC는 CISC에 비교해서 명령어는 적으나 고성능 워크스테이션에 이용되고 있다.", 0, "RISC");
        insertContent(1, "RISC 프로세서는 CISC 프로세서에 비해 프로그래밍이 어려운 반면 처리 속도가 빠르다.", 0, "RISC");
        insertContent(1, "핫 플러깅(Hot Plugging) 기능으로 컴퓨터에 전원이 켜져 있는 상태에도 USB 주변 기기들을 빼거나 연결할 수 있다.", 0, "핫 플러깅(Hot Plugging)");
        insertContent(1, "USB는 플러그 & 플레이(Plug & Play) 기능을 지원한다.", 0, "USB");
        insertContent(1, "Bluetooth: 1994년 스웨던의 에릭슨에 의해 최초로 개발된 근거리 통신 기술로, 휴대폰, PDA, 노트부과 같은 휴대 가능한 장치들 사이의 양방향 정보 전송을 목적으로 한다.", 0, "Bluetooth");
        insertContent(1, "SATA 방식은 직렬 인터페이스 방식을 사용한다.", 0, "SATA");
        insertContent(1, "SATA는 PATA 방식보다 데이터 전송 속도가 빠르다.", 0, "SATA");
        insertContent(1, "메모리 업그레이드란 RAM의 용량을 늘리는 것을 의미하며 실행 속도가 빨라진다.", 0, "메모리 업그레이드");
        insertContent(1, "셰어웨어(Shareware)는 일정기간 무료 사용 후 원하면 정식 프로그램을 구입할 수 있는 형태의 프로그램이다.", 0, "셰어웨어(Shareware)");
        insertContent(1, "프리웨어(Freeware)는 누구나 자유롭게 사용할 수 있는 프로그램으로 기간 및 기능에는 제한이 없다.", 0, "프리웨어(Freeware)");
        insertContent(1, "번들: 특정한 하드웨어나 소프트웨어를 구매하였을 때 끼워주는 소프트웨어", 0, "번들");
        insertContent(1, "윈도우 CE는 가전제품, PDA 등에 사용되는 임베디드 운영체제로 적절하다.", 0, "윈도우 CE");
        insertContent(1, "운영체제는 키보드, 모니터, 디스크 드라이브 등 필수적인 주변장치들을 관리하는 BIOS를 포함한다.", 0, "BIOS");
        insertContent(1, "운영체제의 목적은 처리 능력의 향상, 응답 시간의 단축, 사용 가능도의 향상, 신뢰도 향상 등이다.", 0, "운영체제");
        insertContent(1, "운영체제의 방식에는 일괄 처리, 실시간 처리, 분산 처리 등이 있다.", 0, "운영체제");
        insertContent(1, "실시간 처리(Real Time Processing): 처리할 데이터가 입력될 때 마다 즉시 처리하는 방식으로, 각종 예약 시스템이나 은행 업무 등에 사용한다.", 0, "실시간 처리(Real Time Processing");
        insertContent(1, "Java 언어는 객체 지향 언어로 추상화, 상속화, 다형성과 같은 특징을 가진다.", 0, "Java 언어");
        insertContent(1, "Java 언어는 특정 컴퓨터 구조와 무관한 가상 바이트 머신 코드를 사용하므로 플랫폼이 독립적이다.", 0, "Java 언어");
        insertContent(1, "대표적인 객체 지향 언어로 C++, Java 등이 있다.", 0, "객체 지향 언어");
        insertContent(1, "객체 지향 프로그래밍 언어의 특징으로는 상속성, 캡슐화, 추상화, 다형성 등이 있다.", 0, "객체 지향 프로그래밍 언어");
        insertContent(1, "WML: 무선 접속을 통하여 휴대폰이나 PDA 등에 웹 페이지의 텍스트와 이미지 부분이 표시될 수 있도록 해주는 웹 프로그래밍 언어", 0, "WML");
        insertContent(1, "XML: 기존 HTML 단점을 보완하여 문서의 구조적인 특성들을 고려하여 문서들을 상호 교환할 수 있도록 설계된 언어", 0, "XML");
        insertContent(1, "라우터(Router): 서로 다른 네트워크 간에 자료가 전송될 최적의 길을 찾아주는 역할을 해주는 장치", 0, "라우터(Router)");
        insertContent(1, "링(Ring)형 통신망: 인접한 컴퓨터와 단말기들을 서로 연결하여 양방향으로 데이터 전송이 가능한 통신망", 0, "링(Ring)형 통신망");
        insertContent(1, "인트라넷(Intranet): 인터넷 기술을 이용하여 조직 내의 각종 업무를 수행할 수 있도록 만든 네트워크 환경", 0, "인트라넷(Intranet)");
        insertContent(1, "코덱(CODEC): 음성 또는 영상의 아날로그 신호를 디지털 신호로 변환하거나 그 반대로 디지털 신호를 아날로그 신호로 환원하는 장치", 0, "코덱(CODEC)");
        insertContent(1, "인터넷 주소 체계에서 IPv6는 128 비트의 주소를 사용하여 IPv4의 주소 부족 문제를 해결하였다.", 0, "IPv6");
        insertContent(1, "DNS는 문자로 만들어진 도메인 이름을 숫자로 된 IP 주소로 바꾸는 시스템이다.", 0, "DNS");
        insertContent(1, "인터넷 주소 체계인 IPv6는 인증성, 기밀성, 데이터의 무결성의 지원으로 보안 문제를 해결할 수 있다.", 0, "IPv6");
        insertContent(1, "ARP: 인터넷 주소(IP)를 물리적 하드웨어 주소(MAC) 주소로 변환하는 프로토콜", 0, "ARP");
        insertContent(1, "TCP/IP는 인터넷 연결을 위한 프로토콜이다.", 0, "TCP/IP");
        insertContent(1, "POP3는 메일 서버에 도착한 이메일을 사용자 컴퓨터로 가져올 수 있도록 메일 서버에서 제공하는 프로토콜이다.", 0, "POP3");
        insertContent(1, "SMTP는 사용자의 컴퓨터에서 작성한 메일을 다른 사람의 계정이 있는 곳으로 전송해 주는 역할을 하는 프로토콜이다.", 0, "SMTP");
        insertContent(1, "MIME은 웹 브라우저가 지원하지 않은 각종 멀티미디어 파일의 내용을 확인하고 실행시켜 주는 프로토콜이다.", 0, "MIME");
        insertContent(1, "FTP 서버는 다른 컴퓨터에서 접속하여 파일을 업로드하거나 다운로드하는 기능을 제공하여 주는 컴퓨터를 의미한다.", 0, "FTP 서버");
        insertContent(1, "익명의 계정을 이용하여 파일을 전송할 수 있는 서버를 Anonymous FTP 서버라고 한다.", 0, "Anonymous FTP 서버");
        insertContent(1, "쿠키(Cookie): 웹 사이트에 접속했던 기록 및 사용자의 기본 설정에 대한 정보를 저장하고 있는 텍스트 파일", 0, "쿠키(Cookie)");
        insertContent(1, "RFID: 전파를 이용해 정보를 인식하는 기술로 출입관리, 주차관리에 주로 사용", 0, "RFID");
        insertContent(1, "VoIP: 음성 데이터를 인터넷 프로토콜 데이터 패킷으로 변화하여 일반 데이터망에서 통화를 가능하게 해주는 통신 서비스 기술", 0, "VoIP");
        insertContent(1, "멀티미디어는 정보 제공자와 사용자 간의 의견을 통한 상호 작용에 의해 데이터가 전달되는 쌍방향성의 특징이 있다.", 0, "멀티미디어");
        insertContent(1, "인터레이싱(Interlacing): 그림 파일을 표시할 때 이미지의 대략적인 모습을 먼저 보여준 다음 점차 자세한 모습을 보여주는 기법", 0, "인터레이싱(Interlacing)");
        insertContent(1, "안티앨리어싱(Anti-Aliasing): 이미지의 가장자리가 톱니 모양으로 표현되는 계단 현상을 없애기 위해 경계선을 부드럽게 해주는 필터링 기술", 0, "안티앨리어싱(Anti-Aliasing)");
        insertContent(1, "모핑(Morphing): 2개의 이미지를 부드럽게 연결하여 변환·통합하는 작업", 0, "모핑(Morphing)");
        insertContent(1, "벡터(Vector) 이미지는 이미지의 크기를 확대하여도 화질에 손상이 없다.", 0, "벡터(Vector) 이미지");
        insertContent(1, "벡터(Vertor) 이미지는 대표적으로 WMF 파일 형식이 있다.", 0, "벡터(Vector) 이미지");
        insertContent(1, "JPEG 파일 형식은 사진과 같은 정지영상을 표현하기 위한 국제 표준 압축 방식으로 주로 인터넷에서 사용한다.", 0, "JPEG");
        insertContent(1, "DivX: 영상 부분은 MPEG4를, 음성 부분은 MP3를 채택하여 재조합한 것으로 비표준 동영상 파일 형식이지만 수백 메가에서 기가대의 영화를 압축한 DVD 수준의 고화질 파일", 0, "DivX");
        insertContent(1, "MPEG4: 동영상의 압축 표준안 중에서 IMT-2000 멀티미디어 서비스, 차세대 대화형 인터넷 방송의 핵심 압축 방식으로 비디오/오디오를 압축하기 위한 표준안", 0, "MPEG4");
        insertContent(1, "저작 재산권은 저작자의 생존하는 동안과 저작 시점에 따라 사망 후 70년간 존속한다.", 0, "저작 재산권");
        insertContent(1, "어떤 프로그램이 정상적으로 실행되는 것처럼 속임수를 사용하는 것은 Sniffing이다.", 0, "Sniffing");
        insertContent(1, "네트워크 주변을 지나다니는 패킷을 엿보면서 아이디와 패스워드를 알아내는 것은 Spoofing이다.", 0, "Spoofing");
        insertContent(1, "Proxy Server는 사용자가 방문했던 내용을 담고 있는 캐시 서버로서 방화벽의 기능까지 지원한다.", 0, "Proxy Server");
        insertContent(1, "비밀키 암호화 기법은 대칭키 기법 또는 단일키 암호화 기법이라고도 하며, 대표적으로 DES(Data Encryption Standard)가 있다.", 0, "비밀키 암호화 기법");
        insertContent(1, "공개키 암호화 기법은 비대칭 암호화 기법이라고도 하며, 대표적인 암호화 방식으로 RSA(Rivest Shamir Adleman)가 있다.", 0, "공개키 암호화 기법");
        insertContent(1, "프록시 서버는 네크워크 캐시 기능과 방화벽 기능을 동시에 제공해주는 서버이다.", 0, "프록시 서버");
        insertContent(2, "오늘 날짜를 간단히 입력하기 위해서는 TODAY 함수나 Ctrl과 ;을 누르면 된다.", 0, "TODAY 함수나 Ctrl과 ;");
        insertContent(2, "새 메모를 작성하려면 바로 가기 키 Shift+F2를 누르거나 [검토] 탭 [메모] 그룹에서 ‘새 메모’를 클릭한다.", 0, "Shift+F2");
        insertContent(2, "한 셀에 여러 줄의 데이터를 입력하려면 Alt+Enter를 사용한다.", 0, "Alt+Enter");
        insertContent(2, "같은 데이터를 여러 셀에 한 번에 입력하려면 Ctrl+Enter를 사용한다.", 0, "Ctrl+Enter");
        insertContent(2, "현재 시간 입력은 Ctrl+Shift+;을, 오늘 날짜 입력은 Ctrl+;을 사용한다.", 0, null);
        insertContent(2, "셀에 데이터를 입력하고 Ctrl+←를 누르면 데이터 범위의 제일 왼쪽 셀로 셀 포인터를 이동시킨다.", 0, "Ctrl+←");
        insertContent(2, "단축키 Ctrl+Shift+8: 데이터 테이블 구조를 가진 데이터 목록 전체를 한꺼번에 선택", 0, "Ctrl+Shift+8");
        insertContent(2, "연속적인 위치에 있고, 데이터가 입력되어 있는 여러 개의 셀을 범위로 설정한 후, 셀 병합을 실행하면, 가장 위쪽 또는 왼쪽의 셀 데이터만 남고 나머지 셀 데이터는 모두 지워진다.", 0, "셀 병합");
        insertContent(2, "일반적인 서식 파일의 확장자는 .xltx이고, 매크로가 포함된 서식 파일의 확장자는 .xltm이다.", 0, null);
        insertContent(2, "통합 문서의 기본 서식 파일을 만들려면 XLSTART 폴더에 파일명을 ‘Book’으로 저장한다.", 0, null);
        insertContent(2, "공유된 통합 문서는 여러 사용자가 동시에 변경할 수 있다.", 0, null);
        insertContent(2, "조건부 서식을 만들 때 조건으로 다른 워크시트 또는 통합 문서에 참조는 사용할 수 없다.", 0, "조건부 서식");
        insertContent(2, "동일한 셀 범위에 둘 이상의 조건부 서식 규칙이 True로 평가되어 충돌하는 경우 [조건부 서식 규칙 관리자] 대화 상자의 규칙 목록에서 가장 위에 있는 즉, 우선 순위가 높은 규칙 하나만 적용된다.", 0, "우선 순위가 높은 규칙 하나만 적용");
        insertContent(2, "#DIV/0!: 수식에서 어떤 값을 0으로 나누었을 때 표시되는 오류", 0, "#DIV/0!");
        insertContent(2, "#VALUE!: 수식에서 잘못된 인수나 피연산자를 사용했을 때 표시되는 오류", 0, "#VALUE!");
        insertContent(2, "#NUM!: 수식이나 함수에 잘못된 숫자 값이 포함된 경우", 0, "#NUM!");
        insertContent(2, "같은 통합 문서에서 동일한 이름을 중복하여 사용할 수 없다.", 0, null);
        insertContent(2, "외부 참조에는 통합 문서의 이름과 경로가 포함된다.", 0, null);
        insertContent(2, "SUMIF: 주어진 조건과 일치하는 셀의 합을 구하는 함수", 0, "SUMIF");
        insertContent(2, "COUNT: 인수 중 숫자가 들어 있는 셀의 개수를 구하는 함수", 0, "COUNT");
        insertContent(2, "COUNTA: 인수 중 비어 있지 않은 셀의 개수를 구하는 함수", 0, "COUNTA");
        insertContent(2, "ROUND: 반올림하는 함수", 0, "ROUND");
        insertContent(2, "MOD: 나머지를 구하는 함수", 0, "MOD");
        insertContent(2, "ABS: 절대값을 구하는 함수", 0, "ABS");
        insertContent(2, "CONCATENATE: 여러 텍스트 항목을 하나로 합칠 때 사용하는 함수", 0, "CONCATENATE");
        insertContent(2, "EOMONTH: 지정한 날짜를 기준으로 몇 개월 이전 또는 이후 달의 마지막 날짜를 일련번호로 구하는 함수", 0, "EOMONTH");
        insertContent(2, "EDATE: 지정한 날짜를 기준으로 몇 개월 이전 또는 이후 날짜의 일련번호를 구하는 함수", 0, "EDATE");
        insertContent(2, "VLOOKUP: 범위의 첫번째 열에서 기준값과 같은 데이터를 찾은 후 기준값이 있는 행에서 지정된 열 번호 위치에 있는 데이터를 입력하는 함수", 0, "VLOOKUP");
        insertContent(2, "HLOOKUP: 범위의 첫 번째 행에서 기준값과 같은 데이터를 찾은 후 기준값이 있는 열에서 지정된 행 번호 위치에 있는 데이터를 입력하는 함수", 0, "HLOOKUP");
        insertContent(2, "MATCH: 옵션으로 지정한 방법으로 지정된 범위에서 기준값과 같은 데이터를 찾아 상대 위치를 표시하는 함수", 0, "MATCH");
        insertContent(2, "PMT: 정기적으로 상환할 금액을 구하는 함수", 0, "PMT");
        insertContent(2, "FV: 미래가치를 구하는 함수(매월 일정한 금액을 불입하였을 경우 만기일에 받을 원금과 이자를 계산하는 함수", 0, "FV");
        insertContent(2, "PV: 현재 가치를 구하는 함수", 0, "PV");
        insertContent(2, "배열 수식에서 잘못된 인수나 피연산자를 사용했을 때 #VALUE 에러가 발생한다,", 0, "#VALUE");
        insertContent(2, "배열 수식에서 수식을 입력하고 Ctrl+Shift+Enter를 누르면 수식의 앞뒤에 중괄호({ })가 자동으로 입력된다.", 0, "Ctrl+Shift+Enter");
        insertContent(2, "배열 수식 값은 수식이 아닌 상수이어야 한다.", 0, null);
        insertContent(2, "배열 상수로 숫자, 텍스트, TRUE나 FALSE와 같은 논리 값, #N/A와 같은 오류값 등을 사용할 수 있다.", 0, null);
        insertContent(2, "차트로 작성할 데이터를 범위로 지정한 상태에서 [삽압]→[차트] 그룹에서 차트 종류를 선택하면 워크시트의 가운데에 차트가 삽입된다.", 0, null);
        insertContent(2, "차트를 작성하기 위해서는 원본 데이터를 선택한 후 F11 키를 누르면 새로운 차트 시트에 자동으로 차트가 생성되며, 별도로 설정하지 않았을 경우 기본 차트는 묶은 세로 막대형이다.", 0, "F11");
        insertContent(2, "엑셀 차트의 추세선은 지수, 선형, 로그, 다항식, 거듭제곱, 이동 평균 등 6가지 종류가 있다.", 0, "추세선");
        insertContent(2, "추세선이 추가된 데이터 계열의 차트 종류를 3차원 차트로 변경하면 추세선은 자동으로 삭제된다.", 0, "추세선");
        insertContent(2, "혼합형 차트: 값의 차이가 많은 계열이 차트에 포함된 경우에 사용하며 특정 계열을 강조할 때 사용한다.", 0, "혼합형 차트");
        insertContent(2, "분산형 차트: 항목의 값을 점으로 표시하여 여러 데이터 값들의 관계를 보여주는데 주로 과학 데이터의 차트 작성에 사용한다.", 0, "분산형 차트");
        insertContent(2, "워크시트의 화면 확대/축소 배율은 최소 10%, 최대 400%까지 설정할 수 있다.", 0, "최소 10%, 최대 400%");
        insertContent(2, "[틀 고정] 기능은 데이터의 양이 많은 경우, 특정한 범위의 열 또는 행을 고정시켜 셀 포인터의 이동과 상관없이 화면에 항상 표시할 수 있는 기능이다.", 0, "[틀 고정]");
        insertContent(2, "틀 고정을 수행하면 셀 포인터의 왼쪽과 위쪽으로 틀 고정선이 표시된다,", 0, "틀 고정");
        insertContent(2, "머리글/바닥글을 이용하여 일반 시트의 인쇄 방법과 동일하게 머리글 및 바닥글을 인쇄할 수 있다.", 0, "머리글/바닥글");
        insertContent(2, "[페이지 나누기 미리보기] 상태에서도 데이터의 입력이나 편집을 할 수 있다.", 0, "[페이지 나누기 미리 보기]");
        insertContent(2, "워크시트에 자료가 많을 경우 자동으로 페이지 구분선이 삽입되어 인쇄된다. 임의의 위치에서 페이지를 나누려면 [페이지 레이아웃]→[페이지 설정]→[나누기]→[페이지 나누기 삽입]을 설정하면 된다.", 0, null);
        insertContent(2, "[페이지 설정] 대화상자에서는 용지 방향, 용지 크기, 인쇄 품질을 설정할 수 있다.", 0, "[페이지 설정]");
        insertContent(2, "워크시트에 포함된 도형을 인쇄하고 싶지 않을 때는 인쇄하지 않을 도형을 마우스 오른쪽 버튼을 클릭하여 단축 메뉴에서 [크기 및 속성]을 선택한 후 [속성] 탭에서 ‘개체 인쇄’의 체크 표시를 없앤다.", 0, "워크시트에 포함된 도형을 인쇄하고 싶지 않을 때");
        insertContent(2, "데이터 정렬에서 정렬 조건을 최대 64개까지 지정할 수 있어 다양한 조건으로 정렬할 수 있다.", 0, "정렬 조건을 최대 64개까지");
        insertContent(2, "머리글 행이 없는 데이터도 원하는 기준으로 정렬이 가능하다.", 0, null);
        insertContent(2, "기본 내림차순 정렬은 오류값>논리값>문자>숫자>빈 셀의 순으로 정렬된다.", 0, "오류값>논리값>문자>숫자>빈 셀");
        insertContent(2, "데이터의 자동 필터 기능을 이용해서 같은 열에 여러 개의 항목을 동시에 선택하여 데이터를 추출할 수 있다.", 0, "자동 필터 기능");
        insertContent(2, "데이터의 자동 필터 기능을 이용해서 숫자로만 구성된 하나의 열에 색 기주 필터와 숫자 필터를 동시에 적용할 수 있다.", 0, "자동 필터 기능");
        insertContent(2, "자동 필터는 추출 대상을 전체 필드를 대상으로 하지만, 고급 필터는 특정 필드만으로 대상을 제한할 수 있다.", 0, null);
        insertContent(2, "[외부 데이터 가져오기] 기능을 이용하여 텍스트 파일을 불러오는 경우에 데이터의 구분 기호로 탭, 세미콜론, 쉼표, 공백 등이 기본으로 제공되며, 사용자가 원하는 구분 기호를 설정할 수도 있다.", 0, "[외부 데이터 가져오기]");
        insertContent(2, "워크시트에 외부 데이터를 가져오는 방법으로는 Microsoft Query, 웹 쿼리, 데이터 연결 마법사 등을 사용하는 방법이 있다.", 0, "Microsoft Query, 웹 쿼리, 데이터 연결 마법사 등");
        insertContent(2, "엑셀의 메뉴인 [데이터] 탭 [외부 데이터 가져 오기] 그룹을 이용하여 가져올 수 있는 파일 형식은 Access(*.mdb), 웹(*.htm), XML 데이터(*.xml) 등이 있다.", 0, "Access(*.mdb), 웹(*.htm), XML 데이터(*.xml) 등");
        insertContent(2, "부분합을 작성하려면 첫 행에는 열 이름표가 있어야 하며, 그룹화할 항목을 기준으로 반드시 정렬해야 제대로 된 결과를 얻을 수 있다.", 0, "부분합");
        insertContent(2, "부분합을 제거하면 부분합과 함께 표에 삽입된 윤곽 및 페이지 나누기도 제거된다.", 0, "부분합");
        insertContent(2, "피벗 차트 작성 시 피벗 테이블도 함께 작성된다.", 0, null);
        insertContent(2, "피벗 테이블을 작성할 때 데이터로 외부 데이터를 지정할 수 있다.", 0, "피벗 테이블");
        insertContent(2, "피벗 차트 보고서에 필터를 적용하면 피벗 테이블 보고서에 자동 적용된다.", 0, "피벗 차트 보고서");
        insertContent(2, "시나리오를 사용하여 워크시트 모델의 결과를 예측할 수 있다.", 0, "시나리오");
        insertContent(2, "여러 시나리오를 비교하기 위해 시나리오를 한 페이지의 피벗 테이블로 요약할 수 있다.", 0, "시나리오를 한 페이지의 피벗 테이블로 요약");
        insertContent(2, "데이터 표 기능은 특정한 값이나 수식을 입력한 후 이를 이용하여 표를 자동으로 만들어 주는 기능이다.", 0, "데이터 표 기능");
        insertContent(2, "데이터 표는 특정 값의 변화에 따른 결과값의 변화 과정을 한 번의 연산으로 빠르게 계산하여 표의 형태로 표시해 주는 도구이다.", 0, "데이터 표");
        insertContent(2, "통합은 비슷한 형식의 여러 데이터의 결과를 하나의 표로 통합하여 요약해주는 도구이다.", 0, "통합");
        insertContent(2, "매크로 작성 시 지정한 바로 가기 키는 추후에도 수정이 가능하다.", 0, null);
        insertContent(2, "[매크로 기록] 수행 시 이미 존재하는 매크로명이 있으면 기존 매크로명을 바꿀지의 여부를 확인한다.", 0, "[매크로 기록]");
        insertContent(2, "매크로 이름 지정 시 첫 글자는 반드시 문자로 작성하여야 하고, 두 번째부터 문자, 숫자, 밑줄 문자 등이 사용이 가능하다.", 0, "매크로 이름 지정");
        insertContent(2, "’ActiveX 컨트롤’을 이용하여 매크로를 실행할 경우에는 프로시저를 이용해야 한다.", 0, "프로시저");
        insertContent(2, "사용자 정의 함수는 Function으로 시작해서 End Function으로 끝나도록 되어있다.", 0, "사용자 정의 함수");
        insertContent(2, "프로시저는 연산을 수행하거나 값을 계산하는 일련의 명령문과 메서드로 구성된다.", 0, "프로시저");
        insertContent(2, "변수(Variable)는 컴퓨터가 명령을 처리하는 도중 발생하는 값을 저장하기 위한 공간이다.", 0, "변수(Variable)");
        insertContent(2, "For~Next: 지정한 횟수만큼 명령 코드를 반복하는 비주얼 베이직 제어문", 0, "For~Next");

        insertContent(3, "데이터 조작어(DML:Data Manipulation Language)는 데이터 처리를 위하여 응용 프로그램과 DBMS 사이의 인터페이스를 제공한다.", 0, "데이터 조작어(DML:Data Manipulation Language)");
        insertContent(3, "데이터 조작어(DML:Data Manipulation Language)는 데이터 처리를 위한 연산의 집합으로 데이터의 검색, 삽입, 삭제, 변경 연산 등이 있다.", 0, "데이터 조작어(DML:Data Manipulation Language)");
        insertContent(3, "관계형 데이터 베이스 관리 시스템(RDBMS)의 종류로는 MS-SQL Server, 오라클(Oracle), MY-SQL 등이 있다.", 0, "관계형 데이터 베이스 관리 시스템(RDBMS)");
        insertContent(3, "관계형 데이터베이스에서 튜플은 속성의 모임으로 구성된다.", 0, "튜플");
        insertContent(3, "관계형 데이터베이스에서 속성은 데이터의 가장 작은 논리적 단위이다.", 0, "속성");
        insertContent(3, "관계형 데이터베이스의 테이블에서 동일한 레코드를 중복하여 입력해서는 안된다.", 0, null);
        insertContent(3, "Access의 기본키로 설정된 필드에는 널(NULL) 값이 허용되지 않는다.", 0, "기본키");
        insertContent(3, "외래 키(Foreign Key): 서로 관계를 맺고 있는 릴레이션 R1, R2에서 릴레이션 R2의 한 속성이나 속성의 조합이 릴레이션 R1의 기본키인 것", 0, "외래 키(Foreign Key)");
        insertContent(3, "정규화는 중복되는 값을 일정한 규칙에 의해 추출하여 보다 단순한 형태를 가지는 다수의 테이블로 데이터를 분리하는 작업을 의미한다.", 0, "정규화");
        insertContent(3, "정규화는 릴레이션 속성들 사이의 종속성 개념에 기반을 두고 있다.", 0, "정규화");
        insertContent(3, "개체 관계(Entity RelationShip) 모델은 데이터베이스를 구성하는 개체와 이들 간의 관계를 개념적으로 표시한 모델이다.", 0, "개체 관계(Entity RelationShip) 모델");
        insertContent(3, "액세스(Access)에서 테이블을 만드는 방법은 데이터시트 보기, 디자인 보기, 테이블 가져오기, 테이블 연결 등이 있다.", 0, "데이터시트 보기, 디자인 보기, 테이블 가져오기, 테이블 연결");
        insertContent(3, "테이블 이름과 필드 이름이 동일해서는 안된다.", 0, null);
        insertContent(3, "필드 이름은 영문으로만 작성해야 한다.", 0, "테이블 이름");
        insertContent(3, "데이터 형식 중 텍스트 형식에는 텍스트와 숫자 모두 입력할 수 있다.", 0, "텍스트 형식");
        insertContent(3, "데이터 형식 중 Yes/No 형식은 ‘Yes’ 값에는 ‘-1’이 사용되고, ‘No’ 값에는 ‘0’이 사용된다.", 0, "Yes/No 형식");
        insertContent(3, "테이블에서 이미 작성된 필드의 순서를 변경하려고 할 때 디자인 보기에서 한 번에 여러 개의 필드를 선택한 후 새로운 위치로 드래그 앤 드롭하여 필드를 이동시킬 수 있다.", 0, "디자인 보기에서 한 번에 여러 개의 필드를 선택한 후 새로운 위치로 드래그 앤 드롭");
        insertContent(3, "필드 속성 중 필드 값이 반드시 있어야 하는 경우, 필수 속성을 ‘예’로 설정하면 된다,", 0, "필수 속성을 ‘예’로 설정");
        insertContent(3, "입력 마스크는 텍스트, 숫자, 날짜/시간, 통화 형식에서 사용할 수 있다.", 0, "입력 마스크");
        insertContent(3, "텍스트, 숫자, 일련 번호 형식에서만 필드 크기를 지정할 수 있다.", 0, "필드 크기를 지정");
        insertContent(3, "입력 마스크는 세미콜론(;)으로 나눈 세 개의 부분으로 구성된다.", 0, "입력 마스크");
        insertContent(3, "유효성 검사 규칙: 테이블에 잘못된 데이터가 입력되면 이후 많은 문제가 발생하는 문제를 해결하기 위한 방안. 점검을 필요하는 필드에 요구 사항이나 조건 또는 입력이 가능한 데이터 등을 미리 지정한 후 데이터 입력 시 이를 점검하도록 하는 기능.", 0, "유효성 검사 규칙");
        insertContent(3, "레코드를 추가할 때 기본값을 ‘1’로 지정하면 자동적으로 1이 입력된다.", 0, "레코드를 추가");
        insertContent(3, "기본키는 Null 값을 가질 수 없다. 따라서 반드시 값이 입력되어야 한다.", 0, "기본키");
        insertContent(3, "기본키로 사용된 필드의 값은 변경할 수 없다.", 0, "기본키");
        insertContent(3, "하나의 필드나 필드 조합에 인덱스를 만들어 레코드 찾기와 정렬을 효율적으로 수행할 수 있게 한다.", 0, "인덱스");
        insertContent(3, "색인을 많이 설정하면 테이블의 변경 속도가 저하될 수 있다.", 0, "색인");
        insertContent(3, "중복 불가능(Unique) 색인을 설정하면 중복된 자료의 입력을 방지할 수 있다.", 0, "중복 불가능(Unique) 색인");
        insertContent(3, "데이터베이스에서 인덱스를 사용하는 목적은 데이터 검색 및 정렬 작업 속도 향상이다.", 0, "데이터 검색 및 정렬 작업 속도 향상");
        insertContent(3, "테이블 간의 관계를 설정할 때 일 대 일 관계가 성립하려면 양쪽 테이블의 연결 필드가 모두 중복 불가능의 인덱스나 기본키로 설정되어 있는 경우에 가능하다.", 0, "일 대 일 관계");
        insertContent(3, "테이블의 관계 설정에서 일 대 다 관계는 하나의 테이블에 저장된 대표 값을 다른 테이블에서 여러 번 참조하는 작업에 적합하다.", 0, "일 대 다 관계");
        insertContent(3, "액세스에서 테이블로 가져오거나 연결할 수 있는 파일의 형식은 HTML 문서, Micorsoft Excel 문서, 텍스트 파일 문서 등이 있다.", 0, "HTML 문서, Microsoft Excel 문서, 텍스트 파일 문서 등");
        insertContent(3, "폼에서 [내보내기]를 통해 다른 형식으로 바꾸어 저장하려고 할 때 저장할 수 없있는 형식은 텍스트 파일, Microsoft Excel, HTML 등이 있다.", 0, "텍스트 파일, Microsoft Excel, HTML 등");
        insertContent(3, "액세스의 내보내기(Export)는 테이블이나 쿼리, 폼이나 보고서 등을 다른 형식으로 바꾸어 저장할 수 있다.", 0, "내보내기(Export)");
        insertContent(3, "쿼리는 테이블의 데이터를 이용하여 사용자가 원하는 형식으로 가공하여 보여줄 수 있다.", 0, "쿼리");
        insertContent(3, "ORDER BY절: SELECT 문장에서 한 개 또는 그 이상의 필드를 기준으로 오름차순 또는 내림차순으로 정렬하고자 할 때 사용되는 문", 0, "ORDER BY절");
        insertContent(3, "HAVING절과 WHERE절의 가장 큰 차이점은 그룹 여부이다.", 0, "그룹 여부");
        insertContent(3, "GROUP BY절을 이용하면 설정한 그룹별로 분석할 수 있다.", 0, "GROUP BY");
        insertContent(3, "INSTR(): 문자열에서 특정한 문자 또는 문자열이 존재하는 위치를 알려주는 함수", 0, "INSTR()");
        insertContent(3, "DSUM(): 지정한 레코드 집합에서 해당 필드 값의 합계를 지정할 수 있다.", 0, "DSUM()");
        insertContent(3, "하위 질의: 특정한 테이블을 대상으로 쿼리를 수행한 반환 값을 다른 테이블의 WHERE 절에 이용하는 질의", 0, "하위 질의");
        insertContent(3, "IN: 특정한 필드에서 지정한 값을 가지고 있는 레코드 셋을 반환하고자 할 때 사용", 0, "IN");
        insertContent(3, "SELECT문의 필드 목록이나 WHERE 또는 HAVING 절에서 식 대신에 하위 쿼리를 사용할 수 있다.", 0, "하위 쿼리");
        insertContent(3, "조인(Join)이란 두 개 이상의 테이블을 연결하여 데이터를 검색하는 방법이다.", 0, "조인(Join)");
        insertContent(3, "조인(Join)을 이용하면 정규화를 통해 각 테이블로 분리된 데이터를 통합할 수 있다.", 0, "조인(Join)");
        insertContent(3, "하나의 INSERT문을 이용해 여러 개의 레코드와 필드를 삽입할 수 있다.", 0, "INSERT문");
        insertContent(3, "INSERT문에서 레코드 전체 필드를 추가할 경우 필드 이름을 생략할 수 있다.", 0, "INSERT문");
        insertContent(3, "실행 쿼리는 테이블 만들기 쿼리, 추가 쿼리, 업데이트 쿼리 등이 있다.", 0, "실행 쿼리");
        insertContent(3, "매개 변수 쿼리는 쿼리 실행 시 조건을 입력받아 조건에 맞는 레코드만 반환하는 쿼리이다.", 0, "매개 변수 쿼리");
        insertContent(3, "크로스탭 쿼리는 쿼리 결과를 Excel 워크시트와 비슷한 표 형태로 표시하는 특수한 형식의 쿼리이다.", 0, "크로스탭 쿼리");
        insertContent(3, "폼은 입력 및 편집 작업을 위한 인터페이스이다.", 0, "폼");
        insertContent(3, "폼은 바운드(Boung) 폼과 언바운드(Unbound) 폼이 있다.", 0, "폼");
        insertContent(3, "폼의 구역 중 본문은 실제 데이터를 표시하는 부분으로 ‘연속 폼’의 경우 레코드에 따라 반복적으로 표시된다.", 0, "본문");
        insertContent(3, "열 형식: 폼 마법사에서 선택 가능한 폼의 모양으로 각 필드가 왼쪽의 레이블과 함께 각 행에 나타나며, 폼이 생성된 직후에는 컨트롤 레이아웃이 설정되어 있어 각각의 컨트롤을 다른 크기로 변경할 수 없는 것", 0, "열 형식");
        insertContent(3, "액세서에서 자동으로 폼을 만들어주는 도구는 폼, 폼 분할, 여러 항목 등이 있다.", 0, null);
        insertContent(3, "단일 폼은 한 번에 한 개의 레코드만을 표시한다.", 0, "단일 폼");
        insertContent(3, "연속 폼은 현재 창을 채울 만큼 여러 개의 레코드를 표시한다.", 0, "연속 폼");
        insertContent(3, "폼의 ‘기본 보기’ 속성에는 단일 폼, 연속 폼, 데이터시트 등이 있다.", 0, "폼");
        insertContent(3, "콤보 상자: 폼에서 적은 공간을 차지하면서 데이터 입력이나 검색에 유용하게 사용할 수 있으며 목록의 값과 일치하는 문자열만 입력하도록 제어할 수 있는 것", 0, "콤보 상자");
        insertContent(3, "텍스트 상자(Text Box) 컨트롤은 테이블의 필드 값을 표시하거나 저장할 수 있다.", 0, "텍스트 상자(Text Box)");
        insertContent(3, "폼의 컨트롤을 특정 필드에 바운드시키려면 컨트롤 원본 속성을 이용한다.", 0, null);
        insertContent(3, "하위 폼을 사용하면 일 대 다 관계에 있는 테이블을 효과적으로 표시할 수 있다.", 0, "하위 폼");
        insertContent(3, "하위 폼: 특정한 폼 내에 들어 있는 또 하나의 폼", 0, "하위 폼");
        insertContent(3, "작성된 컨트롤을 클릭한 후 Shift를 누른 상태에서 →를 누르면 컨트롤 크기 조정을 할 수 있다.", 0, "컨트롤 크기 조정");
        insertContent(3, "같은 구역 내에서 컨트롤을 복사하여 붙여넣으면 복사한 컨트롤이 바로 아래에 붙여진다.", 0, null);
        insertContent(3, "폼의 탭 순서(Tab Order)는 폼 보기에서 Tab을 눌렀을 때 각 컨트롤 사이에 이동되는 순서를 설정하는 것이다.", 0, "탭 순서(Tab Order)");
        insertContent(3, "레이블은 폼 작업 시 탭 순서에서 제외된다.", 0, "레이블");
        insertContent(3, "컨트롤 원본: 컨트롤에 연결할 데이터를 지정한다.", 0, "컨트롤 원본");
        insertContent(3, "입력 마스크: 텍스트 상자 컨트롤에 입력할 값의 형식이나 서식을 지정한다.", 0, "입력 마스크");
        insertContent(3, "보고서 구역 중 페이지 머리글 영역에는 열 제목 등을 삽입하며, 모든 페이지의 맨 위에 출력된다.", 0, "페이지 머리글 영역");
        insertContent(3, "보고서 구역 중 그룹 머리글/바닥글 영역에는 일반적으로 그룹별 이름, 요약 정보 등을 삽입한다.", 0, "그룹 머리글/바닥글");
        insertContent(3, "보고서 구역 중 본문 영역은 실제 데이터가 레코드 단위로 반복 출력되는 부분이다.", 0, "본문 영역");
        insertContent(3, "보고서 마법사를 통해 보고서를 작성할 때 그룹 수준 지정, 용지 방향 지정, 스타일 유형 지정 등을 설정할 수 있다.", 0, "그룹 수준 지정, 용지 방향 지정, 스타일 유형 지정");
        insertContent(3, "보고서 보기 형태 중 ‘보고서 보기’는 인쇄 미리 보기와 비슷하지만 페이지의 구분 없이 한 화면에 보고서를 표시한다.", 0, "’보고서 보기’");
        insertContent(3, "보고서의 ‘페이지 설정’창에서 ‘데이터만 인쇄’ 옵션을 선택하면 텍스트 상자에서 표시하는 값만 인쇄된다.", 0, "’데이터만 인쇄’");
        insertContent(3, "보고서에서 그룹 머리글의 ‘반복 실행 구역’ 속성을 ‘예’로 설정한 경우 해당 머리글이 매 페이지마다 표시된다.", 0, "그룹 머리글의 ‘반복 실행 구역’ 속성을 ‘예’로 설정");
        insertContent(3, "액세스 보고서 작성 시 특정 필드를 기준으로 그룹화를 하는 경우 데이터는 그 필드를 기준으로 정렬되어 표시한다.", 0, "그룹화");
        insertContent(3, "크로스탭 보고서: 여러 개의 열로 이루어지고, 그룹 머리글과 그룹 바닥글, 세부 구역이 각 열마다 나타나는 보고서", 0, "크로스탭 보고서");
        insertContent(3, "하위 보고서는 일대다의 관계가 설정되어 있는 테이블의 데이터를 출력할 때 유용하다.", 0, "하위 보고서");


    }

    public void updateLike(SQLiteDatabase db, int id, int islike){
        ContentValues contentValues=new ContentValues();
        if(islike==0) contentValues.put(ISLIKE, 1);
        else if(islike==1) contentValues.put(ISLIKE, 0);
        db.update(TABLE_NAME, contentValues, ID+"="+id, null);
    }

    public SpannableString highlightKeyWord(String content, String keyword){
        if(keyword==null){
            return SpannableString.valueOf(content);
        }
        else{
            SpannableString str = new SpannableString(content);
            str.setSpan(new BackgroundColorSpan(Color.YELLOW), content.indexOf(keyword), content.indexOf(keyword)+keyword.length(), 0);
            return str;
        }
    }

}
