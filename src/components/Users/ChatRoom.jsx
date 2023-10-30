import React, { useEffect, useState } from 'react';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import HeaderMessage from '../Static/HeaderMessage';
import { useUserData } from "../../service/userService";
import { getUserById, getPartenaire } from "../../service/apiService"

var stompClient = null;

const ChatRoom = () => {
    const { userId } = useUserData();
    const [currentChat, setCurrentChat] = useState([]);
    const [userData, setUserData] = useState({
        message: '',
    });
    const [partenaireUsername, setPartenaireUsername] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            if (userId) {
                try {
                    setUserData(await getUserById(userId));
                    setPartenaireUsername(await getPartenaire(userId));
                } catch (error) {
                    console.error(error);
                }
            }
        };
        fetchData();
    }, [userId]);

    useEffect(() => {
        if (userData.username && partenaireUsername) {
            connect();
        }
    }, [userData.username, partenaireUsername]);

    const connect = () => {
        let Sock = new SockJS('http://localhost:8080/ws');
        stompClient = over(Sock);
        stompClient.connect({}, onConnected, onError);
    }

    const onConnected = () => {
        setUserData({ ...userData, "connected": true });
        // Souscrivez à la conversation privée avec le partenaire
        stompClient.subscribe('/user/' + userData.shareCode + '/private', onPrivateMessage);
    }

    const onPrivateMessage = (payload) => {
        const payloadData = JSON.parse(payload.body);
        console.log('Nouveau message reçu :', payloadData);
        setCurrentChat(prevChat => [...prevChat, payloadData]);
    }

    const onError = (err) => {
        console.log(err);
    }

    const handleMessage = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "message": value });
    }

    const sendPrivateValue = () => {
        if (stompClient) {
            const chatMessage = {
                senderName: userData.username,
                receiverName: partenaireUsername,
                message: userData.message,
                status: "MESSAGE"
            };
            stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
            setUserData({ ...userData, "message": "" });
        }
    }

    return (
        <div className="container">
            <HeaderMessage />
            <div className="chat-box">
                <div className="chat-content">
                    <ul className="chat-messages">
                        {currentChat.map((chat, index) => (
                            <li className={`message ${chat.senderName === userData.username && "self"}`} key={index}>
                                {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
                                <div className="message-data">{chat.message}</div>
                                {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
                            </li>
                        ))}
                    </ul>

                    <div className="send-message">
                        <input type="text" className="input-message" placeholder="Entrez votre message" value={userData.message} onChange={handleMessage} />
                        <button type="button" className="send-button" onClick={sendPrivateValue}>Envoyer</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ChatRoom;