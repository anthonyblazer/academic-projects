import React, { useState } from "react";
import { Form, Input, Button, Carousel } from "antd";

export default function App() {
    const [tracks, setTracks] = useState([]);
    const [loading, setLoading] = useState(false);

    // Form Submission Handler
    const onSearch = async (values) => {
        const { searchTerm, limit } = values;
        fetchData(searchTerm, limit);
    };

    // Fetch Data from Spotify API
    async function fetchData(searchTerm, limit) {
        setLoading(true);
        const baseURL = 'https://www.apitutor.org/spotify/simple/v1/search';
        const url = `${baseURL}?q=${searchTerm}&type=track&limit=${limit}`;
        try {
            const response = await fetch(url);
            const data = await response.json();
            setTracks(data);  // Update state to trigger carousel render
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setLoading(false);
        }
    }

    // Convert track data to iframe JSX
    function trackToIframe(track) {
        return (
            <div key={track.id} style={{ textAlign: "center" }}>
                <iframe
                    src={`https://open.spotify.com/embed/track/${track.id}?utm_source=generator`}
                    width="100%"
                    height="352"
                    frameBorder="0"
                    allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture"
                    loading="lazy"
                ></iframe>
                <p>{track.name} - {track.artist.name}</p>
            </div>
        );
    }

    return (
        <div style={{ width: "640px", margin: "auto", paddingTop: "20px" }}>
            <Form
                layout="inline"
                onFinish={onSearch}
                style={{ marginBottom: "20px" }}
            >
                <Form.Item
                    name="searchTerm"
                    rules={[{ required: true, message: "Please enter a search term!" }]}
                >
                    <Input placeholder="Search for songs or artists" />
                </Form.Item>
                <Form.Item
                    name="limit"
                    initialValue={5}
                    rules={[{ required: true, message: "Please specify the number of songs!" }]}
                >
                    <Input type="number" max={20} min={1} placeholder="Number of songs" />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Search
                    </Button>
                </Form.Item>
            </Form>

            {tracks.length > 0 && (
                <Carousel dotPosition="top">
                    {tracks.map(trackToIframe)}
                </Carousel>
            )}
        </div>
    );
}