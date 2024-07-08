import { LoginRequestDto, LoginResponseDto } from "../typedef/login_dto"
import { UserInfoDto } from "../typedef/user_dto"
import TicTacToeRepository from "./tictactoe_repos"

export default class TicTacToeFetchRepository implements TicTacToeRepository {
    async login(request: LoginRequestDto): Promise<LoginResponseDto | null> {
        const formBody = new URLSearchParams();
        formBody.append('userId', request.userId)
        formBody.append('userPw', request.userPw)

        try {
            const host = 'http://localhost:8080/tictactoe'
            const response = await fetch(`${host}/api/login.do`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                credentials: 'include',
                body: formBody.toString()
            });
            if (!response.ok) {
                throw new Error('Network response was not ok')
            }
            return await response.json() as LoginResponseDto
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error)
        }
        return null
    }

    async logout(): Promise<boolean> {
        try {
            const host = 'http://localhost:8080/tictactoe'
            const response = await fetch(`${host}/api/logout.do`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                credentials: 'include',
            })
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.text() == 'true'
            // return await response.json() as UserInfoDto
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error)
        }
        return false
    }

    async getUserInfo(): Promise<UserInfoDto | null> {
        try {
            const host = 'http://localhost:8080/tictactoe'
            const response = await fetch(`${host}/api/userInfo.do`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                credentials: 'include',
            })
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json() as UserInfoDto
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error)
        }
        return null
    }
}