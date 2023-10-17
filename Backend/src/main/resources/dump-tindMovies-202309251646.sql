ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3463 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 235 (class 1259 OID 25307)
-- Name: actor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actor (
    id integer NOT NULL,
    actor_name character varying(255) NOT NULL,
    movie_ids integer[]
);


ALTER TABLE public.actor OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 25306)
-- Name: actor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.actor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.actor_id_seq OWNER TO postgres;

--
-- TOC entry 3464 (class 0 OID 0)
-- Dependencies: 234
-- Name: actor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.actor_id_seq OWNED BY public.actor.id;


--
-- TOC entry 219 (class 1259 OID 16679)
-- Name: badge; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.badge (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    icon character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    user_id bigint
);


ALTER TABLE public.badge OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16678)
-- Name: badge_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.badge_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.badge_id_seq OWNER TO postgres;

--
-- TOC entry 3465 (class 0 OID 0)
-- Dependencies: 218
-- Name: badge_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.badge_id_seq OWNED BY public.badge.id;


--
-- TOC entry 227 (class 1259 OID 16743)
-- Name: commentaire; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commentaire (
    id bigint NOT NULL,
    titre character varying(255) NOT NULL,
    content character varying(255) NOT NULL,
    users_id bigint
);


ALTER TABLE public.commentaire OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16742)
-- Name: commentaire_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.commentaire_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.commentaire_id_seq OWNER TO postgres;

--
-- TOC entry 3466 (class 0 OID 0)
-- Dependencies: 226
-- Name: commentaire_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.commentaire_id_seq OWNED BY public.commentaire.id;


--
-- TOC entry 217 (class 1259 OID 16670)
-- Name: movie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movie (
    id bigint NOT NULL,
    titre character varying(255) NOT NULL,
    dates character varying(255) NOT NULL,
    "durée" character varying(255),
    synopsis text,
    commentaire_id bigint,
    image character varying(255) NOT NULL,
    genre_id integer,
    note_moyenne numeric(3,2) DEFAULT 0.00,
    commentaire character varying(255)
);


ALTER TABLE public.movie OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16669)
-- Name: film_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.film_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.film_id_seq OWNER TO postgres;

--
-- TOC entry 3467 (class 0 OID 0)
-- Dependencies: 216
-- Name: film_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.film_id_seq OWNED BY public.movie.id;


--
-- TOC entry 233 (class 1259 OID 25295)
-- Name: genre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genre (
    id integer NOT NULL,
    nom_genre character varying(255) NOT NULL
);


ALTER TABLE public.genre OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 25294)
-- Name: genre_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.genre_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.genre_id_seq OWNER TO postgres;

--
-- TOC entry 3468 (class 0 OID 0)
-- Dependencies: 232
-- Name: genre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.genre_id_seq OWNED BY public.genre.id;


--
-- TOC entry 229 (class 1259 OID 16767)
-- Name: notation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notation (
    id bigint NOT NULL,
    note integer,
    film_id bigint
);


ALTER TABLE public.notation OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16766)
-- Name: notation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.notation_id_seq OWNER TO postgres;

--
-- TOC entry 3469 (class 0 OID 0)
-- Dependencies: 228
-- Name: notation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notation_id_seq OWNED BY public.notation.id;


--
-- TOC entry 239 (class 1259 OID 25331)
-- Name: note; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.note (
    id integer NOT NULL,
    movie_id integer,
    user_id integer,
    rating integer NOT NULL,
    CONSTRAINT note_rating_check CHECK (((rating >= 1) AND (rating <= 5)))
);


ALTER TABLE public.note OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 25330)
-- Name: note_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.note_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.note_id_seq OWNER TO postgres;

--
-- TOC entry 3470 (class 0 OID 0)
-- Dependencies: 238
-- Name: note_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.note_id_seq OWNED BY public.note.id;


--
-- TOC entry 225 (class 1259 OID 16729)
-- Name: notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notification (
    id bigint NOT NULL,
    titre character varying(255) NOT NULL,
    message character varying(255) NOT NULL,
    dates character varying(255) NOT NULL,
    users_id bigint
);


ALTER TABLE public.notification OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16728)
-- Name: notification_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notification_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.notification_id_seq OWNER TO postgres;

--
-- TOC entry 3471 (class 0 OID 0)
-- Dependencies: 224
-- Name: notification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notification_id_seq OWNED BY public.notification.id;


--
-- TOC entry 223 (class 1259 OID 16710)
-- Name: playlist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.playlist (
    id bigint NOT NULL,
    titre character varying(255) NOT NULL,
    user_id bigint,
    film_id bigint
);


ALTER TABLE public.playlist OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16709)
-- Name: playlist_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.playlist_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.playlist_id_seq OWNER TO postgres;

--
-- TOC entry 3472 (class 0 OID 0)
-- Dependencies: 222
-- Name: playlist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.playlist_id_seq OWNED BY public.playlist.id;


--
-- TOC entry 237 (class 1259 OID 25319)
-- Name: realisator; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realisator (
    id integer NOT NULL,
    realisator_name character varying(255) NOT NULL,
    movie_ids integer[]
);


ALTER TABLE public.realisator OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 25318)
-- Name: realisator_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.realisator_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.realisator_id_seq OWNER TO postgres;

--
-- TOC entry 3473 (class 0 OID 0)
-- Dependencies: 236
-- Name: realisator_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.realisator_id_seq OWNED BY public.realisator.id;


--
-- TOC entry 221 (class 1259 OID 16693)
-- Name: recommendation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recommendation (
    id bigint NOT NULL,
    user_id bigint,
    film_id bigint
);


ALTER TABLE public.recommendation OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16692)
-- Name: recommendation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recommendation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recommendation_id_seq OWNER TO postgres;

--
-- TOC entry 3474 (class 0 OID 0)
-- Dependencies: 220
-- Name: recommendation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recommendation_id_seq OWNED BY public.recommendation.id;


--
-- TOC entry 231 (class 1259 OID 16779)
-- Name: swipe; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.swipe (
    id bigint NOT NULL,
    is_swiped boolean NOT NULL,
    user_id bigint,
    film_id bigint
);


ALTER TABLE public.swipe OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16778)
-- Name: swipe_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.swipe_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.swipe_id_seq OWNER TO postgres;

--
-- TOC entry 3475 (class 0 OID 0)
-- Dependencies: 230
-- Name: swipe_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.swipe_id_seq OWNED BY public.swipe.id;


--
-- TOC entry 215 (class 1259 OID 16661)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    age integer NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    pseudo character varying(255) NOT NULL,
    country character varying(255),
    badge_id bigint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16660)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3476 (class 0 OID 0)
-- Dependencies: 214
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3244 (class 2604 OID 25310)
-- Name: actor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actor ALTER COLUMN id SET DEFAULT nextval('public.actor_id_seq'::regclass);


--
-- TOC entry 3236 (class 2604 OID 24994)
-- Name: badge id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.badge ALTER COLUMN id SET DEFAULT nextval('public.badge_id_seq'::regclass);


--
-- TOC entry 3240 (class 2604 OID 25025)
-- Name: commentaire id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentaire ALTER COLUMN id SET DEFAULT nextval('public.commentaire_id_seq'::regclass);


--
-- TOC entry 3243 (class 2604 OID 25298)
-- Name: genre id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre ALTER COLUMN id SET DEFAULT nextval('public.genre_id_seq'::regclass);


--
-- TOC entry 3234 (class 2604 OID 25056)
-- Name: movie id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie ALTER COLUMN id SET DEFAULT nextval('public.film_id_seq'::regclass);


--
-- TOC entry 3241 (class 2604 OID 25120)
-- Name: notation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notation ALTER COLUMN id SET DEFAULT nextval('public.notation_id_seq'::regclass);


--
-- TOC entry 3246 (class 2604 OID 25334)
-- Name: note id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.note ALTER COLUMN id SET DEFAULT nextval('public.note_id_seq'::regclass);


--
-- TOC entry 3239 (class 2604 OID 25136)
-- Name: notification id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification ALTER COLUMN id SET DEFAULT nextval('public.notification_id_seq'::regclass);


--
-- TOC entry 3238 (class 2604 OID 25168)
-- Name: playlist id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist ALTER COLUMN id SET DEFAULT nextval('public.playlist_id_seq'::regclass);


--
-- TOC entry 3245 (class 2604 OID 25322)
-- Name: realisator id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realisator ALTER COLUMN id SET DEFAULT nextval('public.realisator_id_seq'::regclass);


--
-- TOC entry 3237 (class 2604 OID 25193)
-- Name: recommendation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommendation ALTER COLUMN id SET DEFAULT nextval('public.recommendation_id_seq'::regclass);


--
-- TOC entry 3242 (class 2604 OID 25218)
-- Name: swipe id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.swipe ALTER COLUMN id SET DEFAULT nextval('public.swipe_id_seq'::regclass);


--
-- TOC entry 3233 (class 2604 OID 25243)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3453 (class 0 OID 25307)
-- Dependencies: 235
-- Data for Name: actor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actor (id, actor_name, movie_ids) FROM stdin;
1	Tom Hanks	\N
2	Meryl Streep	\N
3	Leonardo DiCaprio	\N
4	Brad Pitt	\N
5	Margot Robbie	\N
6	Denzel Washington	\N
7	Clint Eastwood	\N
8	Lee Van Cleef	\N
9	Gian Maria Volonté	\N
10	Christian Bale	{7,65}
\.


--
-- TOC entry 3437 (class 0 OID 16679)
-- Dependencies: 219
-- Data for Name: badge; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.badge (id, name, icon, description, user_id) FROM stdin;
\.


--
-- TOC entry 3445 (class 0 OID 16743)
-- Dependencies: 227
-- Data for Name: commentaire; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commentaire (id, titre, content, users_id) FROM stdin;
\.


--
-- TOC entry 3451 (class 0 OID 25295)
-- Dependencies: 233
-- Data for Name: genre; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.genre (id, nom_genre) FROM stdin;
1	Action
2	Comédie
3	Drame
4	Science-fiction
5	Horreur
6	Western
\.


--
-- TOC entry 3435 (class 0 OID 16670)
-- Dependencies: 217
-- Data for Name: movie; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.movie (id, titre, dates, "durée", synopsis, commentaire_id, image, genre_id, note_moyenne, commentaire) FROM stdin;
3	La piel que habito	2011	\N	\N	\N	https://media.senscritique.com/media/000000153186/300/la_piel_que_habito.jpg	\N	0.00	\N
4	La Chasse	2012	\N	\N	\N	https://media.senscritique.com/media/000006199430/300/la_chasse.jpg	\N	0.00	\N
5	Un homme dexception	2001	\N	\N	\N	https://media.senscritique.com/media/000020841679/300/un_homme_d_exception.png	\N	0.00	\N
6	American Gangster	2007	\N	\N	\N	https://media.senscritique.com/media/000007660608/300/american_gangster.jpg	\N	0.00	\N
7	American Psycho	2000	\N	\N	\N	https://media.senscritique.com/media/000014111304/300/american_psycho.jpg	\N	0.00	\N
8	Minority Report	2002	\N	\N	\N	https://media.senscritique.com/media/000016413346/300/minority_report.jpg	\N	0.00	\N
9	Slumdog Millionaire	2008	\N	\N	\N	https://media.senscritique.com/media/000004851880/300/slumdog_millionaire.jpg	\N	0.00	\N
10	Lion	2016	\N	\N	\N	https://media.senscritique.com/media/000004851880/300/slumdog_millionaire.jpg	\N	0.00	\N
12	Donnie Darko	2001	\N	\N	\N	https://media.senscritique.com/media/000019633715/300/donnie_darko.png	\N	0.00	\N
13	Blue Velvet	1986	\N	\N	\N	https://media.senscritique.com/media/000008251023/300/blue_velvet.jpg	\N	0.00	\N
14	Twin Peaks The Missing Pieces	2014	\N	\N	\N	https://media.senscritique.com/media/000019796009/300/twin_peaks_the_missing_pieces.jpg	\N	0.00	\N
15	Mulholland Drive	2001	\N	\N	\N	https://media.senscritique.com/media/000012338911/300/mulholland_drive.jpg	\N	0.00	\N
16	Retour vers le futur	1985	\N	\N	\N	https://media.senscritique.com/media/000012222742/300/retour_vers_le_futur.jpg	\N	0.00	\N
17	Le Temps dun weekend	1992	\N	\N	\N	https://media.senscritique.com/media/000000138412/300/le_temps_d_un_week_end.jpg	\N	0.00	\N
18	Philadelphia	1993	\N	\N	\N	https://media.senscritique.com/media/000012415639/300/philadelphia.jpg	\N	0.00	\N
19	LImpasse	1993	\N	\N	\N	https://media.senscritique.com/media/000009581143/300/l_impasse.png	\N	0.00	\N
20	Jai rencontre le diable	2010	\N	\N	\N	https://media.senscritique.com/media/000004686985/300/j_ai_rencontre_le_diable.jpg	\N	0.00	\N
21	Invictus	2009	\N	\N	\N	https://media.senscritique.com/media/000009456696/300/invictus.jpg	\N	0.00	\N
22	Le Syndicat du crime	1986	\N	\N	\N	https://media.senscritique.com/media/000020218584/300/le_syndicat_du_crime.jpg	\N	0.00	\N
23	American History X	1998	\N	\N	\N	https://media.senscritique.com/media/000012223258/300/american_history_x.jpg	\N	0.00	\N
24	Le Roi Lion	1994	\N	\N	\N	https://media.senscritique.com/media/000013958878/300/le_roi_lion.jpg	\N	0.00	\N
25	Voyage au bout de lenfer	1978	\N	\N	\N	https://media.senscritique.com/media/000014108613/300/voyage_au_bout_de_l_enfer.jpg	\N	0.00	\N
26	Shutter Island	2010	\N	\N	\N	https://media.senscritique.com/media/000007087624/300/shutter_island.jpg	\N	0.00	\N
27	Taxi Driver	1976	\N	\N	\N	https://media.senscritique.com/media/000020193007/300/taxi_driver.jpg	\N	0.00	\N
28	There Will Be Blood	2007	\N	\N	\N	https://media.senscritique.com/media/000000128779/300/there_will_be_blood.jpg	\N	0.00	\N
29	Le Bon la Brute et le Truand	1966	\N	\N	\N	https://media.senscritique.com/media/000008032023/300/le_bon_la_brute_et_le_truand.jpg	\N	0.00	\N
30	La Cite de Dieu	2002	\N	\N	\N	https://media.senscritique.com/media/000019039867/300/la_cite_de_dieu.jpg	\N	0.00	\N
31	The Truman Show	1998	\N	\N	\N	https://media.senscritique.com/media/000007138610/300/the_truman_show.jpg	\N	0.00	\N
32	La Vie est belle	1946	\N	\N	\N	https://media.senscritique.com/media/000020076080/300/la_vie_est_belle.jpg	\N	0.00	\N
33	V pour Vendetta	2006	\N	\N	\N	https://media.senscritique.com/media/000004876958/300/v_pour_vendetta.jpg	\N	0.00	\N
34	Matrix	1999	\N	\N	\N	https://media.senscritique.com/media/000020033620/300/matrix.jpg	\N	0.00	\N
35	Heat	1995	\N	\N	\N	https://media.senscritique.com/media/000007856732/300/heat.jpg	\N	0.00	\N
36	Mystic River	2003	\N	\N	\N	https://media.senscritique.com/media/000013478454/300/mystic_river.jpg	\N	0.00	\N
37	Amadeus	1984	\N	\N	\N	https://media.senscritique.com/media/000017376446/300/amadeus.jpg	\N	0.00	\N
38	Inside Man  LHomme de linterieur	2006	\N	\N	\N	https://media.senscritique.com/media/000004706676/300/inside_man_l_homme_de_l_interieur.jpg	\N	0.00	\N
39	La Vie des autres	2006	\N	\N	\N	https://media.senscritique.com/media/000019230789/300/la_vie_des_autres.jpg	\N	0.00	\N
40	Les Autres	2001	\N	\N	\N	https://media.senscritique.com/media/000019184302/300/les_autres.jpg	\N	0.00	\N
41	Rain Man	1988	\N	\N	\N	https://media.senscritique.com/media/000019034934/300/rain_man.jpg	\N	0.00	\N
42	Gran Torino	2008	\N	\N	\N	https://media.senscritique.com/media/000007087633/300/gran_torino.jpg	\N	0.00	\N
43	Platoon	1986	\N	\N	\N	https://media.senscritique.com/media/000012235259/300/platoon.jpg	\N	0.00	\N
44	Les Nouveaux Sauvages	2014	\N	\N	\N	https://media.senscritique.com/media/000019614870/300/les_nouveaux_sauvages.jpg	\N	0.00	\N
45	Princesse Mononoke	1997	\N	\N	\N	https://media.senscritique.com/media/000020028877/300/princesse_mononoke.jpg	\N	0.00	\N
46	The Game	1997	\N	\N	\N	https://media.senscritique.com/media/000014090183/300/the_game.jpg	\N	0.00	\N
47	Blood Diamond	2006	\N	\N	\N	https://media.senscritique.com/media/000000074192/300/blood_diamond.jpg	\N	0.00	\N
48	La La Land	2016	\N	\N	\N	https://media.senscritique.com/media/000016524046/300/la_la_land.jpg	\N	0.00	\N
49	Sleepers	1996	\N	\N	\N	https://media.senscritique.com/media/000000134287/300/sleepers.jpg	\N	0.00	\N
50	Elephant Man	1980	\N	\N	\N	https://media.senscritique.com/media/000012268597/300/elephant_man.jpg	\N	0.00	\N
51	Raging Bull	1980	\N	\N	\N	https://media.senscritique.com/media/000012223995/300/raging_bull.jpg	\N	0.00	\N
52	Il etait une fois en Amerique	1984	\N	\N	\N	https://media.senscritique.com/media/000020028882/300/il_etait_une_fois_en_amerique.jpg	\N	0.00	\N
53	Casino	1995	\N	\N	\N	https://media.senscritique.com/media/000015404652/300/casino.jpg	\N	0.00	\N
54	La Liste de Schindler	1993	\N	\N	\N	https://media.senscritique.com/media/000012321891/300/la_liste_de_schindler.jpg	\N	0.00	\N
55	Reservoir Dogs	1992	\N	\N	\N	https://media.senscritique.com/media/000013405135/300/reservoir_dogs.png	\N	0.00	\N
56	No Country for Old Men	2007	\N	\N	\N	https://media.senscritique.com/media/000019855627/300/no_country_for_old_men.jpg	\N	0.00	\N
57	Pulp Fiction	1994	\N	\N	\N	https://media.senscritique.com/media/000012288077/300/pulp_fiction.jpg	\N	0.00	\N
58	Les Affranchis	1990	\N	\N	\N	https://media.senscritique.com/media/000008041479/300/les_affranchis.jpg	\N	0.00	\N
59	Scarface	1983	\N	\N	\N	https://media.senscritique.com/media/000007796731/300/scarface.jpg	\N	0.00	\N
60	Les Evades	1994	\N	\N	\N	https://media.senscritique.com/media/000013274439/300/les_evades.png	\N	0.00	\N
61	Pour une poignee de dollars	1964	\N	\N	\N	https://media.senscritique.com/media/000008032047/300/pour_une_poignee_de_dollars.jpg	\N	0.00	\N
62	Vol audessus dun nid de coucou	1975	\N	\N	\N	https://media.senscritique.com/media/000016145216/300/vol_au_dessus_d_un_nid_de_coucou.jpg	\N	0.00	\N
63	Will Hunting	1997	\N	\N	\N	https://media.senscritique.com/media/000007075100/300/will_hunting.jpg	\N	0.00	\N
64	Interstellar	2014	\N	\N	\N	https://media.senscritique.com/media/000018762465/300/interstellar.jpg	\N	0.00	\N
68	Forrest Gump	1994	\N	\N	\N	https://media.senscritique.com/media/000020846881/300/forrest_gump.jpg	\N	0.00	\N
69	American Beauty	1999	\N	\N	\N	https://media.senscritique.com/media/000012536800/300/american_beauty.jpg	\N	0.00	\N
70	Il faut sauver le soldat Ryan	1998	\N	\N	\N	https://media.senscritique.com/media/000008269224/300/il_faut_sauver_le_soldat_ryan.jpg	\N	0.00	\N
71	Les Infiltres	2006	\N	\N	\N	https://media.senscritique.com/media/000000041153/300/les_infiltres.jpg	\N	0.00	\N
72	Le Prestige	2006	\N	\N	\N	https://media.senscritique.com/media/000004699879/300/le_prestige.jpg	\N	0.00	\N
73	Usual Suspects	1995	\N	\N	\N	https://media.senscritique.com/media/000004888256/300/usual_suspects.jpg	\N	0.00	\N
74	Apocalypse Now	1979	\N	\N	\N	https://media.senscritique.com/media/000012235164/300/apocalypse_now.jpg	\N	0.00	\N
75	La Mort aux trousses	1959	\N	\N	\N	https://media.senscritique.com/media/000016132379/300/la_mort_aux_trousses.jpg	\N	0.00	\N
76	Seven	1995	\N	\N	\N	https://media.senscritique.com/media/000012353656/300/seven.jpg	\N	0.00	\N
77	Gladiator	2000	\N	\N	\N	https://media.senscritique.com/media/000012334489/300/gladiator.jpg	\N	0.00	\N
78	Shining	1980	\N	\N	\N	https://media.senscritique.com/media/000019629004/300/shining.jpg	\N	0.00	\N
2	Et pour quelques dollars de plus	1965	2h12	\N	\N	https://media.senscritique.com/media/000008032088/300/et_pour_quelques_dollars_de_plus.jpg	6	0.00	\N
65	The Dark Knight  Le Chevalier noir	2008	\N	\N	\N	https://media.senscritique.com/media/000018762557/300/the_dark_knight_le_chevalier_noir.jpg	1	0.00	\N
66	Inception	2010	\N	\N	\N	https://media.senscritique.com/media/000004710747/300/inception.jpg	1	0.00	\N
67	Fight Club	1999	\N	\N	\N	https://media.senscritique.com/media/000012359351/300/fight_club.jpg	1	0.00	\N
\.


--
-- TOC entry 3447 (class 0 OID 16767)
-- Dependencies: 229
-- Data for Name: notation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notation (id, note, film_id) FROM stdin;
\.


--
-- TOC entry 3457 (class 0 OID 25331)
-- Dependencies: 239
-- Data for Name: note; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.note (id, movie_id, user_id, rating) FROM stdin;
\.


--
-- TOC entry 3443 (class 0 OID 16729)
-- Dependencies: 225
-- Data for Name: notification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notification (id, titre, message, dates, users_id) FROM stdin;
\.


--
-- TOC entry 3441 (class 0 OID 16710)
-- Dependencies: 223
-- Data for Name: playlist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.playlist (id, titre, user_id, film_id) FROM stdin;
\.


--
-- TOC entry 3455 (class 0 OID 25319)
-- Dependencies: 237
-- Data for Name: realisator; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.realisator (id, realisator_name, movie_ids) FROM stdin;
2	Steven Spielberg	\N
3	Quentin Tarantino	\N
1	Christopher Nolan	{64,65,66}
\.


--
-- TOC entry 3439 (class 0 OID 16693)
-- Dependencies: 221
-- Data for Name: recommendation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recommendation (id, user_id, film_id) FROM stdin;
\.


--
-- TOC entry 3449 (class 0 OID 16779)
-- Dependencies: 231
-- Data for Name: swipe; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.swipe (id, is_swiped, user_id, film_id) FROM stdin;
\.


--
-- TOC entry 3433 (class 0 OID 16661)
-- Dependencies: 215
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, age, email, password, pseudo, country, badge_id) FROM stdin;
\.


--
-- TOC entry 3477 (class 0 OID 0)
-- Dependencies: 234
-- Name: actor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.actor_id_seq', 10, true);


--
-- TOC entry 3478 (class 0 OID 0)
-- Dependencies: 218
-- Name: badge_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.badge_id_seq', 1, false);


--
-- TOC entry 3479 (class 0 OID 0)
-- Dependencies: 226
-- Name: commentaire_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.commentaire_id_seq', 1, false);


--
-- TOC entry 3480 (class 0 OID 0)
-- Dependencies: 216
-- Name: film_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.film_id_seq', 78, true);


--
-- TOC entry 3481 (class 0 OID 0)
-- Dependencies: 232
-- Name: genre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.genre_id_seq', 6, true);


--
-- TOC entry 3482 (class 0 OID 0)
-- Dependencies: 228
-- Name: notation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notation_id_seq', 1, false);


--
-- TOC entry 3483 (class 0 OID 0)
-- Dependencies: 238
-- Name: note_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.note_id_seq', 1, false);


--
-- TOC entry 3484 (class 0 OID 0)
-- Dependencies: 224
-- Name: notification_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notification_id_seq', 1, false);


--
-- TOC entry 3485 (class 0 OID 0)
-- Dependencies: 222
-- Name: playlist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.playlist_id_seq', 1, false);


--
-- TOC entry 3486 (class 0 OID 0)
-- Dependencies: 236
-- Name: realisator_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.realisator_id_seq', 3, true);


--
-- TOC entry 3487 (class 0 OID 0)
-- Dependencies: 220
-- Name: recommendation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recommendation_id_seq', 1, false);


--
-- TOC entry 3488 (class 0 OID 0)
-- Dependencies: 230
-- Name: swipe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.swipe_id_seq', 1, false);


--
-- TOC entry 3489 (class 0 OID 0)
-- Dependencies: 214
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- TOC entry 3269 (class 2606 OID 25312)
-- Name: actor actor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actor
    ADD CONSTRAINT actor_pkey PRIMARY KEY (id);


--
-- TOC entry 3253 (class 2606 OID 24996)
-- Name: badge badge_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.badge
    ADD CONSTRAINT badge_pkey PRIMARY KEY (id);


--
-- TOC entry 3261 (class 2606 OID 25027)
-- Name: commentaire commentaire_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentaire
    ADD CONSTRAINT commentaire_pkey PRIMARY KEY (id);


--
-- TOC entry 3251 (class 2606 OID 25058)
-- Name: movie film_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie
    ADD CONSTRAINT film_pkey PRIMARY KEY (id);


--
-- TOC entry 3267 (class 2606 OID 25300)
-- Name: genre genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- TOC entry 3263 (class 2606 OID 25122)
-- Name: notation notation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notation
    ADD CONSTRAINT notation_pkey PRIMARY KEY (id);


--
-- TOC entry 3273 (class 2606 OID 25337)
-- Name: note note_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.note
    ADD CONSTRAINT note_pkey PRIMARY KEY (id);


--
-- TOC entry 3259 (class 2606 OID 25138)
-- Name: notification notification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id);


--
-- TOC entry 3257 (class 2606 OID 25170)
-- Name: playlist playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_pkey PRIMARY KEY (id);


--
-- TOC entry 3271 (class 2606 OID 25324)
-- Name: realisator realisator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realisator
    ADD CONSTRAINT realisator_pkey PRIMARY KEY (id);


--
-- TOC entry 3255 (class 2606 OID 25195)
-- Name: recommendation recommendation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommendation
    ADD CONSTRAINT recommendation_pkey PRIMARY KEY (id);


--
-- TOC entry 3265 (class 2606 OID 25220)
-- Name: swipe swipe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.swipe
    ADD CONSTRAINT swipe_pkey PRIMARY KEY (id);


--
-- TOC entry 3275 (class 2606 OID 25344)
-- Name: note unique_user_movie_rating; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.note
    ADD CONSTRAINT unique_user_movie_rating UNIQUE (user_id, movie_id);


--
-- TOC entry 3249 (class 2606 OID 25245)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3279 (class 2606 OID 25246)
-- Name: badge badge_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.badge
    ADD CONSTRAINT badge_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3285 (class 2606 OID 25251)
-- Name: commentaire commentaire_users_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentaire
    ADD CONSTRAINT commentaire_users_id_fkey FOREIGN KEY (users_id) REFERENCES public.users(id);


--
-- TOC entry 3277 (class 2606 OID 25091)
-- Name: movie film_commentaire_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie
    ADD CONSTRAINT film_commentaire_id_fkey FOREIGN KEY (commentaire_id) REFERENCES public.commentaire(id);


--
-- TOC entry 3278 (class 2606 OID 25301)
-- Name: movie movie_genre_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie
    ADD CONSTRAINT movie_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES public.genre(id);


--
-- TOC entry 3286 (class 2606 OID 25127)
-- Name: notation notation_film_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notation
    ADD CONSTRAINT notation_film_id_fkey FOREIGN KEY (film_id) REFERENCES public.movie(id);


--
-- TOC entry 3289 (class 2606 OID 25338)
-- Name: note note_movie_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.note
    ADD CONSTRAINT note_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movie(id);


--
-- TOC entry 3284 (class 2606 OID 25256)
-- Name: notification notification_users_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_users_id_fkey FOREIGN KEY (users_id) REFERENCES public.users(id);


--
-- TOC entry 3282 (class 2606 OID 25175)
-- Name: playlist playlist_film_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_film_id_fkey FOREIGN KEY (film_id) REFERENCES public.movie(id);


--
-- TOC entry 3283 (class 2606 OID 25261)
-- Name: playlist playlist_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3280 (class 2606 OID 25200)
-- Name: recommendation recommendation_film_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommendation
    ADD CONSTRAINT recommendation_film_id_fkey FOREIGN KEY (film_id) REFERENCES public.movie(id);


--
-- TOC entry 3281 (class 2606 OID 25266)
-- Name: recommendation recommendation_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommendation
    ADD CONSTRAINT recommendation_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3287 (class 2606 OID 25225)
-- Name: swipe swipe_film_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.swipe
    ADD CONSTRAINT swipe_film_id_fkey FOREIGN KEY (film_id) REFERENCES public.movie(id);


--
-- TOC entry 3288 (class 2606 OID 25271)
-- Name: swipe swipe_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.swipe
    ADD CONSTRAINT swipe_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3276 (class 2606 OID 25282)
-- Name: users users_badge_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_badge_id_fkey FOREIGN KEY (badge_id) REFERENCES public.badge(id);


-- Completed on 2023-09-25 16:46:32

--
-- PostgreSQL database dump complete
--

