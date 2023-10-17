-- Ajoutez une contrainte d'unicité sur les colonnes "user_id" et "movie_id"
ALTER TABLE public.note
ADD CONSTRAINT unique_user_movie_rating UNIQUE (user_id, movie_id);

--recup la liste filmographie de l'acteur
select m.* from movie m join actor a on m.id = any(a.movie_ids) where a.id = 10

-- TRIGGER pour les swippe--
CREATE OR REPLACE FUNCTION check_swipe_direction()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.swipeDirection NOT IN ('left', 'right') THEN
    RAISE EXCEPTION 'La valeur de swipeDirection doit être "left" ou "right"';
  END IF;

  IF NEW.swipeDirection = 'right' AND NEW.notLiked = true THEN
    RAISE EXCEPTION 'Si swipeDirection est "right", liked doit être vrais';
  END IF;

  IF NEW.swipeDirection = 'left' AND NEW.liked = true THEN
    RAISE EXCEPTION 'Si swipeDirection est "left", liked doit être faux';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_swipe_direction_trigger
BEFORE INSERT ON swipe
FOR EACH ROW
EXECUTE FUNCTION check_swipe_direction();
