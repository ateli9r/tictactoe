package tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeGameTest {

    public TicTacToeGameTest() {

    }

    /**
     * 게임 생성
     * - 게임 룸을 생성한다
     */
    @Test
    public void testCreateGameRoom() {
        /*
         * # 전제조건
         * - 로그인이 되어 있다
         * 
         * # 종료조건
         * - 게임 룸이 생성 되어야 한다
         */

        //  메인 – 게임 방 접속	T-05-0013	게임 방 생성	게임 방 생성 버튼 클릭 시 게임 방 이름을 입력 할 수 있는 창이 출력 되며, 이름 입력 후 생성 버튼을 클릭하면 게임방이 생성 된다.	

    }

    /**
     * 게임 참가
     * - 게임 룸에 참가한다
     */
    @Test
    public void testJoinGameRoom() {
        /*
         * # 전제조건
         * - 참가할 게임 룸이 생성되어 있다
         * 
         * # 종료조건
         * - 게임 룸에 세션의 아이디가 등록되고 참가하고자 하는
         * 게임룸의 정보가 매핑된 게임 진행 페이지로 이동되어야 한다
         */


        //  메인 – 게임 방 접속	T-05-0014	기존게임 접속	기존 게임 중 게임대기 상태인 방의 JOIN GAME 버튼을 클릭하면 해당 게임 방의 후공(X)으로 접속 된다.
        // 게임진행	T-06-0001	게임 대기	게임 방 생성 후, 상대방 PC의 참여 전까지 게임 방에서 대기상태로 접속된다.        
        // 게임진행	T-06-0002	게임 접속	기존 게임에 입장 하면 해당 게임 방의 후공(X)으로 접속되며, 게임이 시작 된다.        
    }

    /**
     * 게임 진행
     * - 게임 룸의 게임 정보를 업데이트
     */
    @Test
    public void testUpdateGameRoom() {
        /*
         * # 전제조건
         * - 진행중인 게임 룸 정보가 매핑된 게임 진행 페이지에
         * 접속되어 있으며 세션에 로그인된 사용자의 차례이다
         * 
         * # 종료조건
         * - 상대방 사용자의 차례가 되거나 게임의 결과가 확정된다
         */

        //  게임진행	T-06-0003	차례 변경	차례가 변경 될 때 마다 차례인 유저를 표시하여 준다.
        // 게임진행	T-06-0004	실시간 게임 진행	틱택토 게임을 통신으로 진행한다.	
        // 게임진행	T-06-0005	게임 결과 출력	게임이 완료 되면 각 유저에게 결과 창이 출력 되며, 갱신된 승률 정보가 표시된다.	
        // 게임진행	T-06-0006	게임 다시시작	게임 완료 후, 다시시작 버튼을 누르면 게임대기 상태로 돌아간다. 상대역시 다시시작 버튼을 누르면 게임이 시작 된다.      
        // 게임진행	T-06-0007	게임 나가기	게임 완료 후, 나가기 버튼을 누르면 메인 페이지로 이동한다.                  
        // 게임진행 - 로직	T-07-0001	예외처리-1	매칭이 완료 된 게임 방에서는 다른 플레이어가 입장 할 수 없다. (JOIN GAME버튼을 없애기 / 입장 불가 안내 창 띄우기 등.)
        // 게임진행 - 로직	T-07-0002	예외처리-2	자신의 차례가 아닌 사용자는 게임 판에 영향을 줄 수 없다.
        // 게임진행 - 로직	T-07-0003	예외처리-3	이미 마크 된 셀에는 영향을 줄 수 없다.
        // 게임진행 - 로직	T-07-0004	(O)승리조건 – 수평	플레이어(O)가 수평, 수직, 대각선을 채울 때 플레이어(O)가 우승 처리된다.
        // 게임진행 - 로직	T-07-0005	(X)승리조건 – 수평	플레이어(X)가 수평, 수직, 대각선을 채울 때 플레이어(X)가 우승 처리된다.
        // 게임진행 - 로직	T-07-0006	승리/패배 처리	우승 처리가 진행 된 후, 우승 한 플레이어는 승리안내 팝업을, 우승하지 못한 플레이어에게는 패배안내 팝업이 출력된다.
        // 게임진행 - 로직	T-07-0007	무승부 조건	9개의 셀이 모두 채워 졌을 때, 플레이어(O), 플레이어(X) 모두가 승리조건을 채우지 못했을 경우, 무승부 처리된다.
        // 게임진행 - 로직	T-07-0008	무승부 처리	무승부가 된 게임은 플레이어 모두에게 무승부안내 팝업이 출력된다.
        // 게임진행 - 로직	T-07-0009	승률 갱신	승리, 무승부, 패배 처리에 맞게 승률 정보가 수정된다.

    }

    /**
     * 게임 정보 조회
     * - 게임 정보를 조회한다
     */
    @Test
    public void testViewGameRoom() {
        /*
         * # 전제조건
         * - 진행중인 게임 룸 정보가 매핑된 게임 진행 페이지에 접속되어 있다
         * 
         * # 종료조건
         * - 게임 정보를 반영한다
         */

        // 게임진행	T-06-0008	퇴장 후 유저, 게임 방 정보 갱신	유저가 게임을 끝마치고 게임방에서 퇴장하면 유저의 승률정보, 랭킹정보가 갱신 된다.	        
    }

    /**
     * 게임 리스트 조회
     * - 게임 룸 리스트를 조회한다
     */
    @Test
    public void testListGameRoom() {
        /*
         * # 전제조건
         * - 게임 리스트 페이지에 접속해 있다
         * 
         * # 종료조건
         * - 게임 리스트를 반영한다
         */

        //  메인	T-05-0004	게임 방 리스트 출력	가장 최근에 만들어진 방부터 순서대로 게임방의 리스트가 메인 좌측 하단에 출력 된다.	
        //  메인	T-05-0005	게임 방 리스트 페이징	최근에 만들어진 방을 3개씩 묶어서 출력하며, 보이지 않는 방들은 페이징 처리한다.	
        // 메인 – 게임 방 접속	T-05-0012	게임 방 리스트 새로고침	게임 방 리스트에서 새로고침 클릭 시, 게임 방 리스트 정보가 갱신 된다.         
        
    }
    
}