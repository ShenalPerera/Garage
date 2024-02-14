import Header from "../Header/Header";
import {Container, Grid} from "@mui/material";

let Layout = (props)=> {
    let {children} = props;

    return (
        <>
            <Header />
            <Container style={{marginTop:'80px'}}>
                {children}
            </Container>
        </>

    );
}

export default Layout;