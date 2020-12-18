import Data.List 
import Data.Maybe
import System.Random

--Seed
temp = 300
teams = permutations ["BS","CM","CH","CV","CS","DS","EE","HU","MA","ME","PH","ST"]!!temp

--Store first team and second teams of the matches
first_team = [teams!!i | i<-[0,2..length(teams)-1]]
second_team = [teams!!i | i<-[1,3..length(teams)-1]]

-- Store times and dates of the matches
times = ["9:30", "7:30"]
dates = ["1-12-2020", "2-12-2020", "3-12-2020"]
-- This is to just compare in 24 hours format in nextMatch function
day_time = [(1,9.5),(1,19.5),(2,9.5),(2,19.5),(3,9.5),(3,19.5)]

spaces = "   "
-- Function to print Fixture
print_fixture :: String->Int->IO()
print_fixture x 6 = putStrLn ("No match found for team "++x )
print_fixture x match_nu = if x == first_team!!match_nu || x == second_team!!match_nu then
        putStrLn (first_team!!match_nu   ++ spaces ++ "vs"  ++ spaces ++ second_team!!match_nu ++ spaces ++ spaces++ dates!!(match_nu `div` 2) ++ spaces++spaces++times!!(match_nu `mod`2))
        else do
        print_fixture x (match_nu+1)

print_fixtures:: Int -> IO()
print_fixtures 6 = putStrLn ("")
-- Print Fixture according to the match number
print_fixtures match_nu = do
        putStrLn (first_team!!match_nu   ++ spaces ++ "vs"  ++ spaces ++ second_team!!match_nu ++ spaces ++ spaces++ dates!!(match_nu `div` 2) ++ spaces++spaces++times!!(match_nu `mod`2))
        print_fixtures (match_nu+1)


-- Print all fixtures starting from match 0
fixture :: String -> IO ()
fixture "all" = print_fixtures 0
fixture x = do
        print_fixture x 0

-- Print nextMatch if match time is just after the current day and time
printNextMatch :: Int->Float->Int->IO()  
printNextMatch _ _ 6 = putStrLn ("There is no next match of stage 1.")
printNextMatch date time match_nu = do
        if (date,time)<=day_time!!match_nu then 
           putStrLn (first_team!!match_nu   ++ spaces ++ "vs"  ++ spaces ++ second_team!!match_nu ++ spaces ++ spaces++ dates!!(match_nu `div` 2) ++ spaces++spaces++times!!(match_nu `mod`2)) else do
           printNextMatch date time (match_nu+1)
           
-- Get nextmatch from current date and time           
nextMatch :: Int->Float->IO()
nextMatch date time = do
       if time<0.0 then putStrLn ("Time can not be less than 0")
       else if time >24.0 then putStrLn ("Time can not be greater than 24")
       else do
          if date <= 0 then putStrLn ("Date in December can not be less than 1")
          else if date >31 then putStrLn ("Date in December can not be greater than 31")
          else do printNextMatch date time 0
